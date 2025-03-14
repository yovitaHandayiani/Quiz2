package quiz.navigation.animation

import android.animation.PropertyValuesHolder
import android.animation.TypeEvaluator
import android.animation.ValueAnimator
import android.graphics.Color
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import quiz.navigation.R
import quiz.navigation.databinding.ActivityAnimationBinding

class AnimationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAnimationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnimationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.animateButton.setOnClickListener {
            //1. Simple Float Animation
            // Create a simple float animation from 0f to 100f
//            val animator = ValueAnimator.ofFloat(0f, 100f)
//            animator.duration = 1000 // 1 second
//
//            animator.addUpdateListener { animation ->
//                val value = animation.animatedValue as Float
//                binding.animatedView.translationX = value
//            }
//            animator.start()

            // Animates between float values
//            val animator = ValueAnimator.ofFloat(0f, 360f)
//            animator.duration = 1000
//            animator.addUpdateListener { animation ->
//                val angle = animation.animatedValue as Float
//                binding.animatedView.rotation = angle
//            }
//            animator.start()

//===========================================================================================================================================

            //2. Integer Animation
            // Animates between integer values
//            val animator = ValueAnimator.ofInt(0, 255)
//            animator.duration = 1000
//            animator.addUpdateListener { animation ->
//                val value = animation.animatedValue as Int
//                binding.animatedView.setBackgroundColor(Color.rgb(value, 0, 0,))
//            }
//            animator.start()

//===========================================================================================================================================

            //3. ofArgb()
            // Animates between colors
//            val animator = ValueAnimator.ofArgb(Color.RED, Color.BLUE)
//            animator.duration = 1500
//            animator.addUpdateListener { animation ->
//                val color = animation.animatedValue as Int
//                binding.animatedView.setBackgroundColor(color)
//            }
//            animator.start()

//===========================================================================================================================================

            //4. ofObject()
            //5. ofPropertyValuesHolder()

//===========================================================================================================================================
/*
            animator.duration = 1000 // in milliseconds
            animator.startDelay = 500 // start after 500ms
            animator.repeatCount = 3 // repeat 3 times

            // Or for infinite animation:
            animator.repeatCount = ValueAnimator.INFINITE

            // Repeat modes
            animator.repeatMode = ValueAnimator.RESTART // default, start from beginning

            // or
            animator.repeatMode = ValueAnimator.REVERSE // play backwards on repeat

            // Linear interpolator (constant rate)
            animator.interpolator = LinearInterpolator()

            // Accelerate interpolator (start slow, end fast)
            animator.interpolator = AccelerateInterpolator()

            // Decelerate interpolator (start fast, end slow)
            animator.interpolator = DecelerateInterpolator()

            // Accelerate/Decelerate interpolator (start slow, middle fast, end slow)
            animator.interpolator = AccelerateDecelerateInterpolator()

            // Anticipate interpolator (slight backward motion before moving forward)
            animator.interpolator = AnticipateInterpolator()

            // Overshoot interpolator (goes beyond the final value and comes back)
            animator.interpolator = OvershootInterpolator()

            // Anticipate Overshoot interpolator (combines both effects)
            animator.interpolator = AnticipateOvershootInterpolator()

            // Bounce interpolator (bounces at the end)
            animator.interpolator = BounceInterpolator()

            // Custom interpolation timing with a Path
            val path = Path()
            path.cubicTo(0.2f, 0f, 0.1f, 1f, 1f, 1f)
            animator.interpolator = PathInterpolator(path)

            // Or directly with control points
            animator.interpolator = PathInterpolator(0.2f, 0f, 0.1f, 1f)
 */

//===========================================================================================================================================
        }
    }
}