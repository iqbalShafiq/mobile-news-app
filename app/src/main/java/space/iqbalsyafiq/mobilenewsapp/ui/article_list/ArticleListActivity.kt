package space.iqbalsyafiq.mobilenewsapp.ui.article_list

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import space.iqbalsyafiq.mobilenewsapp.databinding.ActivityArticleListBinding

class ArticleListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityArticleListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArticleListBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}