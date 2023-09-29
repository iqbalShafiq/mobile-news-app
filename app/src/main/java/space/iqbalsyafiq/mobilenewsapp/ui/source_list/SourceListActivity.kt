package space.iqbalsyafiq.mobilenewsapp.ui.source_list

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import space.iqbalsyafiq.mobilenewsapp.databinding.ActivitySourceListBinding

class SourceListActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySourceListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySourceListBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}