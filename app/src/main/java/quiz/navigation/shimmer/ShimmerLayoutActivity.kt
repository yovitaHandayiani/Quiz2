package quiz.navigation.shimmer

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import quiz.navigation.R
import android.os.Handler
import android.os.Looper
import android.view.View
import quiz.navigation.databinding.ActivityShimmerLayoutBinding

class ShimmerLayoutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityShimmerLayoutBinding
    private var isLoading = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShimmerLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Start shimmer effect on app launch
        startShimmerEffect()

        // Simulate data loading
        Handler(Looper.getMainLooper()).postDelayed({
            stopShimmerEffect()
            showContent()
            isLoading = false
        }, 5000) // 3 seconds delay to simulate network call

        // Toggle button to switch between loading and content
        binding.toggleButton.setOnClickListener {
            isLoading = !isLoading

            if (isLoading) {
                hideContent()
                startShimmerEffect()
            } else {
                stopShimmerEffect()
                showContent()
            }
        }
    }

    private fun startShimmerEffect() {
        binding.shimmerFrameLayout.visibility = View.VISIBLE
        binding.shimmerFrameLayout.startShimmer()
    }

    private fun stopShimmerEffect() {
        binding.shimmerFrameLayout.stopShimmer()
        binding.shimmerFrameLayout.visibility = View.GONE
    }

    private fun showContent() {
        binding.contentCardView.visibility = View.VISIBLE
    }

    private fun hideContent() {
        binding.contentCardView.visibility = View.GONE
    }

    override fun onResume() {
        super.onResume()
        if (isLoading) {
            binding.shimmerFrameLayout.startShimmer()
        }
    }

    override fun onPause() {
        binding.shimmerFrameLayout.stopShimmer()
        super.onPause()
    }
}