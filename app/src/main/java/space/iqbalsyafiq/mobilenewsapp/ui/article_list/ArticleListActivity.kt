package space.iqbalsyafiq.mobilenewsapp.ui.article_list

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel
import space.iqbalsyafiq.mobilenewsapp.R
import space.iqbalsyafiq.mobilenewsapp.adapter.ArticleListAdapter
import space.iqbalsyafiq.mobilenewsapp.adapter.LoadingStateAdapter
import space.iqbalsyafiq.mobilenewsapp.databinding.ActivityArticleListBinding
import space.iqbalsyafiq.mobilenewsapp.ui.article_detail.ArticleDetailActivity
import space.iqbalsyafiq.mobilenewsapp.ui.source_list.SourceListActivity
import space.iqbalsyafiq.mobilenewsapp.util.textChanges

class ArticleListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityArticleListBinding
    private val viewModel: ArticleListViewModel by viewModel()
    private lateinit var adapter: ArticleListAdapter
    private var source = ""

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArticleListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        source = intent.getStringExtra(SourceListActivity.EXTRA_SOURCE) ?: ""
        setupToolbar()

        initAdapter()
        binding.rvArticleList.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                adapter.retry()
            }
        )

        loadArticles()

        // load articles again when error message is clicked
        binding.tvErrorMessage.setOnClickListener {
            loadArticles()
        }
        binding.etSearch.textChanges()
            .filterNot {
                it.isNullOrBlank()
            }
            .debounce(1500)
            .distinctUntilChanged { old, new ->
                old != new
            }
            .onEach {
                loadArticles()
            }
            .launchIn(lifecycleScope)
    }

    private fun setupToolbar() {
        binding.tvToolbar.apply {
            text = source
            setOnClickListener { onBackPressedDispatcher.onBackPressed() }
        }
    }

    private fun initAdapter() {
        adapter = ArticleListAdapter()
        adapter.apply {
            onItemClick = {
                Intent(
                    this@ArticleListActivity,
                    ArticleDetailActivity::class.java
                ).apply {
                    putExtra(EXTRA_ARTICLE_URL, it.url)
                    startActivity(this)
                }
            }

            addLoadStateListener {
                onLoadingState(it)
            }
        }
    }

    private fun loadArticles() {
        viewModel.loadArticles(
            binding.etSearch.text.toString(),
            source
        ).observe(this) {
            adapter.submitData(lifecycle, it)
            Log.d(TAG, "onCreate articles data: $it")
        }
    }

    private fun onLoadingState(loadState: CombinedLoadStates) {
        if (loadState.refresh is LoadState.Loading) {
            Log.d(TAG, "onLoadingState: Should be loading")
            binding.progressLoading.visibility = View.VISIBLE
            binding.tvLabel.visibility = View.GONE
            binding.tvErrorMessage.visibility = View.GONE
            binding.rvArticleList.visibility = View.GONE
        } else {
            onFetchError(loadState)
            binding.progressLoading.visibility = View.GONE
        }
    }

    private fun onFetchError(loadState: CombinedLoadStates) {
        if (
            loadState.refresh is LoadState.Error ||
            loadState.append is LoadState.Error
        ) {
            Log.d(TAG, "onFetchError: Should be error")
            binding.tvErrorMessage.visibility = View.VISIBLE
            binding.tvErrorMessage.text = getString(R.string.no_internet_access_state_message)

            binding.progressLoading.visibility = View.GONE
            binding.tvLabel.visibility = View.GONE
            binding.rvArticleList.visibility = View.GONE
        } else {
            binding.tvLabel.visibility = View.VISIBLE
            binding.rvArticleList.visibility = View.VISIBLE
            binding.tvErrorMessage.visibility = View.GONE
            binding.progressLoading.visibility = View.GONE
        }

        onFetchEmpty(loadState)
    }

    private fun onFetchEmpty(loadState: CombinedLoadStates) {
        if (loadState.append.endOfPaginationReached) {
            if (adapter.itemCount < 1) {
                Log.d(TAG, "onFetchEmpty: Should be empty")
                binding.tvErrorMessage.visibility = View.VISIBLE
                binding.tvErrorMessage.text = getString(R.string.empty_article_state_message)

                binding.progressLoading.visibility = View.GONE
                binding.tvLabel.visibility = View.GONE
                binding.rvArticleList.visibility = View.GONE
            } else {
                binding.tvLabel.visibility = View.VISIBLE
                binding.rvArticleList.visibility = View.VISIBLE
                binding.tvErrorMessage.visibility = View.GONE
                binding.progressLoading.visibility = View.GONE
            }
        }
    }

    companion object {
        private val TAG = ArticleListActivity::class.java.simpleName
        const val EXTRA_ARTICLE_URL = "Article URL"
    }
}