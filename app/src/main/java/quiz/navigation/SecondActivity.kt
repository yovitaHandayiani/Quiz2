package quiz.navigation

import android.animation.Animator
import android.animation.AnimatorInflater
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
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

        rotationAnimator.setInterpolator(AccelerateDecelerateInterpolator())
        // Start the animation
        rotationAnimator.start()

        binding.bellImageView.setOnClickListener {
            val animatorX = ObjectAnimator.ofFloat(binding.bellImageView, "pivotX", 30f).apply {
                repeatCount = 5
                repeatMode = ObjectAnimator.REVERSE
                duration = 100
            }

            val animatorY = ObjectAnimator.ofFloat(binding.bellImageView, "pivotY", 30f).apply {
                repeatCount = 5
                repeatMode = ObjectAnimator.REVERSE
                duration = 100
            }

            val animatorScaleX =
                ObjectAnimator.ofFloat(binding.bellImageView, "rotation", -5f, 5f).apply {
                    repeatCount = 5
                    repeatMode = ObjectAnimator.REVERSE
                    duration = 100
                }

            val set = AnimatorSet()
            set.playTogether(animatorX, animatorY, animatorScaleX)

            // Add an AnimatorListener to reset the rotation after the animation ends
            set.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    // Reset the rotation to 0 degrees
                    binding.bellImageView.rotation = 0f
                }
            })
            set.setInterpolator(AccelerateDecelerateInterpolator())
            set.start()
        }

        binding.button.setOnTouchListener { view, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    // Set the overlay color to semi-transparent black
                    binding.button.background.setColorFilter(
                        Color.argb(15, 0, 0, 0),
                        PorterDuff.Mode.SRC_ATOP
                    )

                    view.animate()
                        .scaleX(0.8f)
                        .scaleY(0.8f)
                        .setDuration(150)
                        .setInterpolator(AccelerateDecelerateInterpolator())
                        .start()
                }

                MotionEvent.ACTION_UP -> {
                    // Remove the overlay color
                    binding.button.background.clearColorFilter()

                    view.animate()
                        .scaleX(1f)
                        .scaleY(1f)
                        .setDuration(150)
                        .setInterpolator(AccelerateDecelerateInterpolator())
                        .start()
                    view.performClick()
                }

                MotionEvent.ACTION_CANCEL -> {
                    // Remove the overlay color
                    binding.button.background.clearColorFilter()

                    view.animate()
                        .scaleX(1f)
                        .scaleY(1f)
                        .setDuration(150)
                        .setInterpolator(AccelerateDecelerateInterpolator())
                        .start()
                }
            }

            return@setOnTouchListener true
        }

        // Get reference to the BottomNavigationView
        val bottomNavView = binding.bottomNavigation

        // Get the ChildCount of BottomNavigationView (it's a ViewGroup)
        val bottomNavChildCount = bottomNavView.childCount

        // The first child is usually a LinearLayout containing the menu items
        if (bottomNavChildCount > 0) {
            val bottomNavChildView = bottomNavView.getChildAt(0)

            if (bottomNavChildView is ViewGroup) {
                // Loop through each child of the LinearLayout (each menu item)
                for (i in 0 until bottomNavChildView.childCount) {
                    val itemView = bottomNavChildView.getChildAt(i)

                    // Set OnTouchListener for each menu item
                    itemView.setOnTouchListener { view, event ->
                        when (event.action) {
                            MotionEvent.ACTION_DOWN -> {
                                // Scale down animation
                                view.animate()
                                    .scaleX(0.8f)
                                    .scaleY(0.8f)
                                    .setDuration(150)
                                    .setInterpolator(AccelerateDecelerateInterpolator())
                                    .start()
                            }

                            MotionEvent.ACTION_UP -> {
                                // Scale back to normal size
                                view.animate()
                                    .scaleX(1f)
                                    .scaleY(1f)
                                    .setDuration(150)
                                    .setInterpolator(AccelerateDecelerateInterpolator())
                                    .start()

                                // Ensure click is still performed
                                view.performClick()
                            }

                            MotionEvent.ACTION_CANCEL -> {
                                // Scale back to normal size
                                view.animate()
                                    .scaleX(1f)
                                    .scaleY(1f)
                                    .setDuration(150)
                                    .setInterpolator(AccelerateDecelerateInterpolator())
                                    .start()
                            }
                        }

                        return@setOnTouchListener true
                    }
                }
            }
        }

        // Set up normal item selection listener if needed
        bottomNavView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.item_1 -> {
                    // Handle Home item click
                    true
                }
                R.id.item_2 -> {
                    // Handle Cart item click
                    true
                }
                R.id.item_3 -> {
                    // Handle Barcode item click
                    true
                }
                R.id.item_4 -> {
                    // Handle History item click
                    true
                }
                R.id.item_5 -> {
                    // Handle Favorite item click
                    true
                }
                else -> false
            }
        }

    }

}

//class CustomImageView @JvmOverloads constructor(
//    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
//) : AppCompatImageView(context, attrs, defStyleAttr) {
//
//    override fun onTouchEvent(event: MotionEvent): Boolean {
//        when (event.action) {
//            MotionEvent.ACTION_DOWN -> {
//                // Delay the scaling animation to allow the ripple effect to show
//                postDelayed({
//                    animate()
//                        .scaleX(0.8f)
//                        .scaleY(0.8f)
//                        .setDuration(100)
//                        .start()
//                }, 30) // Delay of 50ms (adjust as needed)
//            }
//
//            MotionEvent.ACTION_UP -> {
//                animate()
//                    .scaleX(1f)
//                    .scaleY(1f)
//                    .setDuration(100)
//                    .start()
//                performClick()
//            }
//
//            MotionEvent.ACTION_CANCEL -> {
//                animate()
//                    .scaleX(1f)
//                    .scaleY(1f)
//                    .setDuration(100)
//                    .start()
//            }
//        }
//        return super.onTouchEvent(event)
//    }
//}