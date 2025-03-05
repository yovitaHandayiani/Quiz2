package quiz.navigation

import android.content.Intent
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.graphics.drawable.shapes.RectShape
import android.graphics.drawable.shapes.RoundRectShape
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
//        binding.viewPager.setPageTransformer(ZoomOutPageTransformer())
        binding.viewPager.setPageTransformer(DepthPageTransformer())

        TabLayoutMediator(binding.materialTabLayout, binding.viewPager) { tab, position ->

        }.attach()

        //Update the button text based on current page
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                val onBoardingData = OnBoardingDataProvider.onBoardingItems[position]
                binding.btnNextSatu.text = onBoardingData.buttonText
            }
        })

        binding.btnNextSatu.setOnClickListener {
            // Get the current position of the ViewPager2 from the activity using the method
            val currentPosition = (this as? ActivityOnBoarding)?.getViewPager()?.currentItem ?: 0

            // Calculate the next position
            val nextPosition = currentPosition + 1

            // Check if the next position is within bounds (i.e., not exceeding the number of items)
            if (nextPosition < (this as? ActivityOnBoarding)?.getViewPager()?.adapter?.itemCount ?: 0) {
                // Move the ViewPager2 to the next position
                (this as? ActivityOnBoarding)?.getViewPager()?.setCurrentItem(nextPosition, true)
            }else{
                val intent = Intent(this, MainActivity::class.java);
//                this.finish()
                this.startActivity(intent)
            }
        }


        binding.btnSkip.setOnClickListener {
//            val intent = Intent(this, MainActivity::class.java);
//            startActivity(intent)
            (this.getViewPager()?.setCurrentItem(this.getViewPager()?.adapter?.itemCount ?: 0, true))
        }

    }

    fun getViewPager(): ViewPager2 {
        return binding.viewPager
    }
}