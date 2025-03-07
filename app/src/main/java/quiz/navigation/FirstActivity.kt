package quiz.navigation

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import quiz.navigation.databinding.ActivityFirstBinding

class FirstActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFirstBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFirstBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set a click listener on the image
        binding.smallImageView.setOnClickListener {
            // Create an intent to open the second activity
            val intent = Intent(this, SecondActivity::class.java)

            // Create a bundle to hold the shared element information
            val options = android.app.ActivityOptions.makeSceneTransitionAnimation(
                this,
                binding.smallImageView, // The shared element
                "sharedImage" // The unique transition name
            )

            // Start the second activity with the shared element transition
            startActivity(intent, options.toBundle())
        }
    }
}