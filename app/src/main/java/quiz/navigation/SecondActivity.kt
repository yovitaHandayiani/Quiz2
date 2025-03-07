package quiz.navigation

import android.animation.AnimatorInflater
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import quiz.navigation.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // No special code needed here, the shared element transition happens automatically

        binding.bigImageView.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        // Load the animation
        val rotationAnimator = AnimatorInflater.loadAnimator(this, R.animator.rotate_animation)

        // Set the target (the star image view)
        rotationAnimator.setTarget(binding.starImageView)

        // Start the animation
        rotationAnimator.start()
    }
}