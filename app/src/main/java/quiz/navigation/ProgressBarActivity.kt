package quiz.navigation

import android.animation.ValueAnimator
import android.os.Bundle
import android.text.InputFilter
import android.text.Spanned
import android.view.animation.DecelerateInterpolator
import android.widget.SeekBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import quiz.navigation.databinding.ActivityProgressBarBinding

class ProgressBarActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProgressBarBinding
    private var currentStep = 0

    // Define the checkpoint values
    private val checkpointValues = arrayOf(30, 60, 120, 240, 400)
    private val finalCheckpointValue = checkpointValues.last() // The last checkpoint value
    private val maxAllowedValue = 500 // Maximum value user can input

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

        // Set up numeric input filter for the EditText
        binding.valueInput.filters = arrayOf(object : InputFilter {
            override fun filter(
                source: CharSequence, start: Int, end: Int,
                dest: Spanned, dstart: Int, dend: Int
            ): CharSequence? {
                try {
                    val input = (dest.toString() + source.toString()).trim()
                    if (input.isEmpty()) return null

                    val value = input.toInt()
                    if (value > maxAllowedValue) {
                        Toast.makeText(applicationContext, "Maximum allowed value is $maxAllowedValue", Toast.LENGTH_SHORT).show()
                        return ""
                    }
                    return null
                } catch (e: NumberFormatException) {
                    return ""
                }
            }
        })

        // Set up the submit button for the custom value
        binding.submitButton.setOnClickListener {
            val inputText = binding.valueInput.text.toString().trim()
            if (inputText.isEmpty()) {
                Toast.makeText(this, "Please enter a value", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            try {
                val inputValue = inputText.toInt()
                if (inputValue > maxAllowedValue) {
                    Toast.makeText(this, "Value cannot exceed $maxAllowedValue", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                // Convert the value to progress percentage
                val progressPercentage = valueToProgress(inputValue)

                // Update the progress bar
                binding.customProgressBar.setProgress(progressPercentage)

                // Update the SeekBar to reflect the same progress
                binding.seekBar.progress = progressPercentage.toInt()

                // Display the calculated percentage for debugging
                binding.progressPercentage.text = "Progress: ${progressPercentage.toInt()}%"

            } catch (e: NumberFormatException) {
                Toast.makeText(this, "Please enter a valid number", Toast.LENGTH_SHORT).show()
            }
        }

        // Set up button to increment progress through checkpoints
        binding.nextButton.setOnClickListener {
            val maxProgress = binding.customProgressBar.getMaxProgress()
            val currentProgress = binding.customProgressBar.getCurrentProgress()

            if (currentProgress >= maxProgress) {
                // If progress is already at 100%, reset it to 0
                binding.customProgressBar.setProgress(0f)
                binding.seekBar.progress = 0
                currentStep = 0 // Reset the step counter
                binding.progressPercentage.text = "Progress: 0%"
            } else {
                if (currentStep < checkpointValues.size) {
                    // Get the next checkpoint value
                    val nextValue = checkpointValues[currentStep]

                    // Convert value to progress percentage
                    val nextProgress = valueToProgress(nextValue)

                    // Update the UI
                    binding.customProgressBar.setProgress(nextProgress)
                    binding.seekBar.progress = nextProgress.toInt()
                    binding.progressPercentage.text = "Progress: ${nextProgress.toInt()}%"

                    currentStep++
                } else {
                    // After last step, fill to 100%
                    binding.customProgressBar.setProgress(maxProgress)
                    binding.seekBar.progress = maxProgress.toInt()
                    binding.progressPercentage.text = "Progress: 100%"
                }
            }
        }
    }

    /**
     * Converts a value (0-500) to a progress percentage (0-100)
     * This handles the non-linear nature of the checkpoints
     */
    private fun valueToProgress(value: Int): Float {
        // Handle edge cases
        if (value <= 0) return 0f

        // Special handling for values equal to the last checkpoint
        if (value == finalCheckpointValue) {
            // Get the position of the last circle
            val circlePositions = binding.customProgressBar.getCirclePositions()
            return circlePositions.last() * 100f
        }

        if (value > finalCheckpointValue) {
            // For values above the final checkpoint, calculate progress proportionally
            // between the last checkpoint and 100%
            val circlePositions = binding.customProgressBar.getCirclePositions()
            val lastPosition = circlePositions.last()

            // Calculate how far beyond the final checkpoint we are
            val excessProgress = (value - finalCheckpointValue).toFloat() / (maxAllowedValue - finalCheckpointValue)

            // Interpolate between the last checkpoint position and 100%
            return (lastPosition + excessProgress * (1f - lastPosition)) * 100f
        }

        // Find the checkpoint positions
        val circlePositions = binding.customProgressBar.getCirclePositions()

        // Find which segment the value falls into
        for (i in 0 until checkpointValues.size - 1) {
            val lowerValue = checkpointValues[i]
            val upperValue = checkpointValues[i + 1]

            if (value in lowerValue until upperValue) {
                // Calculate how far between these checkpoints the value is
                val segmentProgress = (value - lowerValue).toFloat() / (upperValue - lowerValue)

                // Calculate the progress bar position
                val lowerPosition = circlePositions[i]
                val upperPosition = circlePositions[i + 1]

                // Interpolate between the two checkpoint positions
                val progressPosition = lowerPosition + segmentProgress * (upperPosition - lowerPosition)

                // Convert to percentage of total progress
                return progressPosition * 100f
            }
        }

        // If we can't find a segment, use linear interpolation as fallback
        return (value.toFloat() / maxAllowedValue) * 100f
    }
}