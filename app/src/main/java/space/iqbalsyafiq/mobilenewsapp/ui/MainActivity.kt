package space.iqbalsyafiq.mobilenewsapp.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import space.iqbalsyafiq.mobilenewsapp.databinding.ActivityMainBinding
import space.iqbalsyafiq.mobilenewsapp.ui.category_list.CategoryListActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // give delay to the splash screen before move to category list screen
        lifecycleScope.launch {
            delay(2000)
            Intent(
                this@MainActivity,
                CategoryListActivity::class.java
            ).apply {
                startActivity(this)
            }
        }
    }
}