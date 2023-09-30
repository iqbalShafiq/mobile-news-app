package space.iqbalsyafiq.mobilenewsapp.ui.category_list

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import space.iqbalsyafiq.mobilenewsapp.constants.Categories
import space.iqbalsyafiq.mobilenewsapp.databinding.ActivityCategoryListBinding
import space.iqbalsyafiq.mobilenewsapp.ui.source_list.SourceListActivity

class CategoryListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCategoryListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            cvBusiness.onCategoryClickListener(Categories.BUSINESS)
            cvEntertainment.onCategoryClickListener(Categories.ENTERTAINMENT)
            cvGeneral.onCategoryClickListener(Categories.GENERAL)
            cvHealth.onCategoryClickListener(Categories.HEALTH)
            cvScience.onCategoryClickListener(Categories.SCIENCE)
            cvSports.onCategoryClickListener(Categories.SPORTS)
            cvTechnology.onCategoryClickListener(Categories.TECHNOLOGY)
        }
    }

    private fun CardView.onCategoryClickListener(category: String) {
        setOnClickListener {
            Intent(this@CategoryListActivity, SourceListActivity::class.java).apply {
                putExtra(EXTRA_CATEGORY, category)
                startActivity(this)
            }
        }
    }

    companion object {
        const val EXTRA_CATEGORY = "Category"
    }
}