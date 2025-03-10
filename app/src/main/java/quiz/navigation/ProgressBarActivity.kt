package quiz.navigation

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.animation.DecelerateInterpolator
import android.widget.SeekBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import quiz.navigation.databinding.ActivityProgressBarBinding

class ProgressBarActivity : AppCompatActivity() {
    private lateinit var binding : ActivityProgressBarBinding
    private var currentStep = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProgressBarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up SeekBar to control the progress
        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding.customProgressBar.setProgress(progress.toFloat())
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        // Set initial progress
        binding.seekBar.progress = 0

        // Set up button to increment progress or reset
        binding.nextButton.setOnClickListener {
            val circlePositions = binding.customProgressBar.getCirclePositions()
            val maxProgress = binding.customProgressBar.getMaxProgress()
            val currentProgress = binding.customProgressBar.getCurrentProgress()

            if (currentProgress >= maxProgress) {
                // If progress is already at 100%, reset it to 0
                binding.customProgressBar.setProgress(0f)
                currentStep = 0 // Reset the step counter
            } else {
                if (currentStep < circlePositions.size) {
                    // Step-by-step progress: move to the next circle position
                    val nextProgress = circlePositions[currentStep] * maxProgress
                    binding.customProgressBar.setProgress(nextProgress)
                    currentStep++
                } else {
                    // After last step, fill to 100%
                    binding.customProgressBar.setProgress(maxProgress)
                }
            }
        }
    }

}