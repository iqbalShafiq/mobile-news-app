package space.iqbalsyafiq.mobilenewsapp.ui.article_detail

import android.os.Bundle
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import space.iqbalsyafiq.mobilenewsapp.databinding.ActivityArticleDetailBinding
import space.iqbalsyafiq.mobilenewsapp.ui.article_list.ArticleListActivity

class ArticleDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityArticleDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArticleDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val articleUrl = intent.getStringExtra(ArticleListActivity.EXTRA_ARTICLE_URL) ?: ""
        loadPage(articleUrl)

        with(binding) {
            tvToolbar.setOnClickListener { onBackPressedDispatcher.onBackPressed() }
            ivRefresh.setOnClickListener { loadPage(articleUrl) }
        }
    }

    private fun loadPage(articleUrl: String) {
        with(binding) {
            wvArticleDetail.webViewClient = WebViewClient()
            wvArticleDetail.loadUrl(articleUrl)
        }
    }
}