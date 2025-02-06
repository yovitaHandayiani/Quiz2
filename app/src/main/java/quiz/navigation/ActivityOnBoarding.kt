package quiz.navigation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import quiz.navigation.databinding.ActivityOnBoardingBinding

class ActivityOnBoarding : AppCompatActivity() {
    private lateinit var binding: ActivityOnBoardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContentView(R.layout.activity_on_boarding)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets

        binding = ActivityOnBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = ViewPager2Adapter(this)
        binding.viewPager.adapter = adapter
        binding.viewPager.isUserInputEnabled = true

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                Log.d("PageChanged", "Page selected: $position")
            }
        })

        TabLayoutMediator(binding.materialTabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> {
                    binding.btnSkip.setOnClickListener {
                        val intent = Intent(this, MainActivity::class.java);
                        startActivity(intent)
                    }
                }

                1 -> {
                    binding.btnSkip.setOnClickListener {
                        val intent = Intent(this, MainActivity::class.java);
                        startActivity(intent)
                    }
                }

                2 -> {
                    binding.btnSkip.setOnClickListener {
                        val intent = Intent(this, MainActivity::class.java);
                        startActivity(intent)
                    }
                }
            }
        }.attach()

    }
}