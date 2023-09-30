package space.iqbalsyafiq.mobilenewsapp.ui.source_list

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel
import space.iqbalsyafiq.mobilenewsapp.R
import space.iqbalsyafiq.mobilenewsapp.adapter.LoadingStateAdapter
import space.iqbalsyafiq.mobilenewsapp.adapter.SourceListAdapter
import space.iqbalsyafiq.mobilenewsapp.databinding.ActivitySourceListBinding
import space.iqbalsyafiq.mobilenewsapp.model.response.sources.Source
import space.iqbalsyafiq.mobilenewsapp.ui.article_list.ArticleListActivity
import space.iqbalsyafiq.mobilenewsapp.ui.category_list.CategoryListActivity
import space.iqbalsyafiq.mobilenewsapp.util.textChanges

class SourceListActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySourceListBinding
    private val viewModel: SourceListViewModel by viewModel()
    private lateinit var adapter: SourceListAdapter
    private var category: String = ""
    private lateinit var pagingData: PagingData<Source>

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySourceListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // set category from intent
        category = intent.getStringExtra(CategoryListActivity.EXTRA_CATEGORY) ?: ""

        // set toolbar content and navigation
        setupToolbar()

        // init adapter
        initAdapter()

        // attach adapter into recycler view
        binding.rvSourceList.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                adapter.retry()
            }
        )

        // load sources from service
        loadSources()

        // load sources again when error message is clicked
        binding.tvErrorMessage.setOnClickListener {
            loadSources()
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
                loadSources()
            }
            .launchIn(lifecycleScope)
    }

    private fun setupToolbar() {
        binding.tvToolbar.apply {
            text = category
            setOnClickListener { onBackPressedDispatcher.onBackPressed() }
        }
    }

    private fun initAdapter() {
        adapter = SourceListAdapter()
        adapter.apply {
            onItemClick = {
                Intent(
                    this@SourceListActivity,
                    ArticleListActivity::class.java
                ).apply {
                    putExtra(EXTRA_SOURCE, it.id)
                    startActivity(this)
                }
            }

            addLoadStateListener {
                onLoadingState(it)
            }
        }
    }

    private fun loadSources() {
        viewModel.loadSources(
            binding.etSearch.text.toString(),
            category
        ).observe(this) {
            pagingData = it
            adapter.submitData(lifecycle, it)
            Log.d(TAG, "onCreate sources data: $it")
        }
    }

    private fun onLoadingState(loadState: CombinedLoadStates) {
        if (
            loadState.refresh is LoadState.Loading
        ) {
            Log.d(TAG, "onLoadingState: Should be loading")
            binding.progressLoading.visibility = View.VISIBLE
            binding.tvLabel.visibility = View.GONE
            binding.tvErrorMessage.visibility = View.GONE
            binding.rvSourceList.visibility = View.GONE
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
            binding.rvSourceList.visibility = View.GONE
        } else {
            binding.tvLabel.visibility = View.VISIBLE
            binding.rvSourceList.visibility = View.VISIBLE
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
                binding.tvErrorMessage.text = getString(R.string.empty_state_message)

                binding.progressLoading.visibility = View.GONE
                binding.tvLabel.visibility = View.GONE
                binding.rvSourceList.visibility = View.GONE
            } else {
                binding.tvLabel.visibility = View.VISIBLE
                binding.rvSourceList.visibility = View.VISIBLE
                binding.tvErrorMessage.visibility = View.GONE
                binding.progressLoading.visibility = View.GONE
            }
        }
    }

    companion object {
        private val TAG = SourceListActivity::class.java.simpleName
        const val EXTRA_SOURCE = "Source"
    }
}