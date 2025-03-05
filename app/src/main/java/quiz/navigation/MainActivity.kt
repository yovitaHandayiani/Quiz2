package quiz.navigation

import android.animation.*
import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.view.animation.*
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.core.util.Pair
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.FlingAnimation
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import quiz.navigation.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupClickListeners()
    }

    private fun setupClickListeners() {
        // Hide all animation targets initially
        hideAllTargets()

        // Property Animation examples
        binding.valueAnimatorButton.setOnClickListener {
            hideAllTargets()
            binding.valueAnimatorTarget.visibility = View.VISIBLE
            showValueAnimatorExample()
        }

        binding.objectAnimatorButton.setOnClickListener {
            hideAllTargets()
            binding.objectAnimatorTarget.visibility = View.VISIBLE
            showObjectAnimatorExample()
        }

        binding.animatorSetButton.setOnClickListener {
            hideAllTargets()
            binding.animatorSetTarget.visibility = View.VISIBLE
            showAnimatorSetExample()
        }

        binding.viewPropertyButton.setOnClickListener {
            hideAllTargets()
            binding.viewPropertyTarget.visibility = View.VISIBLE
            showViewPropertyAnimatorExample()
        }

        // View Animation example
        binding.tweenAnimationButton.setOnClickListener {
            hideAllTargets()
            binding.tweenAnimationTarget.visibility = View.VISIBLE
            showTweenAnimationExample()
        }

        // Physics-Based Animation examples
        binding.springAnimationButton.setOnClickListener {
            hideAllTargets()
            binding.springAnimationTarget.visibility = View.VISIBLE
            showSpringAnimationExample()
        }

        binding.flingAnimationButton.setOnClickListener {
            hideAllTargets()
            binding.flingAnimationTarget.visibility = View.VISIBLE
            showFlingAnimationExample()
        }

        // Drawable Animation examples
        binding.frameAnimationButton.setOnClickListener {
            hideAllTargets()
            binding.frameAnimationTarget.visibility = View.VISIBLE
            showFrameByFrameAnimationExample()
        }

        binding.vectorAnimationButton.setOnClickListener {
            hideAllTargets()
            binding.vectorAnimationTarget.visibility = View.VISIBLE
            showAnimatedVectorDrawableExample()
        }

        // Motion Layout example
        binding.motionLayoutButton.setOnClickListener {
            startActivity(Intent(this, MotionLayoutActivity::class.java))
        }

        // Skeleton Loader example
        binding.skeletonLoaderButton.setOnClickListener {
            hideAllTargets()
            binding.skeletonContainer.visibility = View.VISIBLE
            showSkeletonLoaderExample()
        }

        // Shared Element example
        binding.sharedElementButton.setOnClickListener {
            startSharedElementActivity()
        }
    }

    private fun hideAllTargets() {
        binding.valueAnimatorTarget.visibility = View.GONE
        binding.objectAnimatorTarget.visibility = View.GONE
        binding.animatorSetTarget.visibility = View.GONE
        binding.viewPropertyTarget.visibility = View.GONE
        binding.tweenAnimationTarget.visibility = View.GONE
        binding.springAnimationTarget.visibility = View.GONE
        binding.flingAnimationTarget.visibility = View.GONE
        binding.frameAnimationTarget.visibility = View.GONE
        binding.vectorAnimationTarget.visibility = View.GONE
        binding.skeletonContainer.visibility = View.GONE
    }

    // 1. ValueAnimator Example
    private fun showValueAnimatorExample() {
        val target = binding.valueAnimatorTarget

        // Create a ValueAnimator that animates from 0 to 255
        val colorAnimator = ValueAnimator.ofInt(0, 255)
        colorAnimator.duration = 1500

        // Use AccelerateDecelerateInterpolator for smooth easing
        colorAnimator.interpolator = AccelerateDecelerateInterpolator()

        // Update the background color during animation
        colorAnimator.addUpdateListener { animator ->
            val value = animator.animatedValue as Int
            target.setBackgroundColor(android.graphics.Color.rgb(255, value, 50))
        }

        // Add start and end listeners
        colorAnimator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator) {
                target.text = "Starting"
            }

            override fun onAnimationEnd(animation: Animator) {
                target.text = "Finished"
                // Restart the animation after a delay
                target.postDelayed({
                    colorAnimator.start()
                }, 500)
            }
        })

        // Start the animation
        colorAnimator.start()
    }

    // 2. ObjectAnimator Example
    private fun showObjectAnimatorExample() {
        val target = binding.objectAnimatorTarget

        // Create an ObjectAnimator that targets the 'rotation' property
        val rotationAnimator = ObjectAnimator.ofFloat(target, "rotation", 0f, 360f)
        rotationAnimator.duration = 2000

        // Use a BounceInterpolator for bouncy effect at the end
        rotationAnimator.interpolator = BounceInterpolator()

        // Make it repeat infinitely
        rotationAnimator.repeatCount = ObjectAnimator.INFINITE
        rotationAnimator.repeatMode = ObjectAnimator.RESTART

        // Start the animation
        rotationAnimator.start()
    }

    // 3. AnimatorSet Example
    private fun showAnimatorSetExample() {
        val target = binding.animatorSetTarget

        // Create multiple animations
        val scaleX = ObjectAnimator.ofFloat(target, "scaleX", 1.0f, 1.5f)
        val scaleY = ObjectAnimator.ofFloat(target, "scaleY", 1.0f, 1.5f)
        val rotateButton = ObjectAnimator.ofFloat(target, "rotation", 0f, 360f)
        val fadeButton = ObjectAnimator.ofFloat(target, "alpha", 1.0f, 0.5f, 1.0f)

        // Set durations
        scaleX.duration = 1000
        scaleY.duration = 1000
        rotateButton.duration = 1000
        fadeButton.duration = 1000

        // Set interpolators for easing
        scaleX.interpolator = OvershootInterpolator()
        scaleY.interpolator = OvershootInterpolator()
        rotateButton.interpolator = AccelerateInterpolator()
        fadeButton.interpolator = AnticipateInterpolator()

        // Create and configure AnimatorSet
        val animatorSet = AnimatorSet()

        // Run scaleX and scaleY in parallel, then rotateButton, then fadeButton
        animatorSet.play(scaleX).with(scaleY)
        animatorSet.play(rotateButton).after(scaleX)
        animatorSet.play(fadeButton).after(rotateButton)

        // Add a listener to repeat the animation
        animatorSet.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                // Reset properties and restart after a delay
                target.postDelayed({
                    target.scaleX = 1.0f
                    target.scaleY = 1.0f
                    target.rotation = 0f
                    target.alpha = 1.0f
                    animatorSet.start()
                }, 1000)
            }
        })

        // Start the animation
        animatorSet.start()
    }

    // 4. ViewPropertyAnimator Example
    private fun showViewPropertyAnimatorExample() {
        val target = binding.viewPropertyTarget

        // Reset position
        target.translationX = 0f
        target.translationY = 0f
        target.alpha = 1.0f
        target.scaleX = 1.0f
        target.scaleY = 1.0f

        // Chain animations using ViewPropertyAnimator for more concise code
        target.animate()
            .translationXBy(200f)
            .translationYBy(100f)
            .scaleX(0.5f)
            .scaleY(0.5f)
            .alpha(0.7f)
            .setDuration(1000)
            .setInterpolator(FastOutSlowInInterpolator())
            .withEndAction {
                // Create a new animation sequence when the first one finishes
                target.animate()
                    .translationX(0f)
                    .translationY(0f)
                    .scaleX(1.0f)
                    .scaleY(1.0f)
                    .alpha(1.0f)
                    .setDuration(1000)
                    .setInterpolator(OvershootInterpolator())
                    .withEndAction {
                        // Restart the whole sequence after a delay
                        target.postDelayed({
                            showViewPropertyAnimatorExample()
                        }, 500)
                    }
                    .start()
            }
            .start()
    }

    // 5. View Animation (Tween Animation) Example
    private fun showTweenAnimationExample() {
        val target = binding.tweenAnimationTarget

        // Create a set of animations
        val animationSet = AnimationSet(true)

        // Create a rotation animation
        val rotate = RotateAnimation(
            0f, 360f,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        )

        // Create a scale animation
        val scale = ScaleAnimation(
            1.0f, 1.5f, 1.0f, 1.5f,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        )

        // Create an alpha animation
        val alpha = AlphaAnimation(1.0f, 0.5f)

        // Configure animations
        rotate.duration = 2000
        rotate.interpolator = AccelerateDecelerateInterpolator()

        scale.duration = 2000
        scale.interpolator = OvershootInterpolator()

        alpha.duration = 2000
        alpha.repeatCount = 1
        alpha.repeatMode = Animation.REVERSE

        // Add animations to the set
        animationSet.addAnimation(rotate)
        animationSet.addAnimation(scale)
        animationSet.addAnimation(alpha)

        // Set a listener to restart the animation
        animationSet.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {
                target.postDelayed({
                    target.startAnimation(animationSet)
                }, 500)
            }

            override fun onAnimationRepeat(animation: Animation?) {}
        })

        // Start the animation
        target.startAnimation(animationSet)
    }

    // 6. Spring Animation Example
    private fun showSpringAnimationExample() {
        val target = binding.springAnimationTarget

        // Create a spring animation for translateX
        val springAnimX = SpringAnimation(target, DynamicAnimation.TRANSLATION_X, 200f)

        // Configure the spring force
        val springForceX = SpringForce(200f)
        springForceX.dampingRatio = SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY
        springForceX.stiffness = SpringForce.STIFFNESS_MEDIUM

        // Assign the spring force to the animation
        springAnimX.spring = springForceX

        // Create a spring animation for translateY
        val springAnimY = SpringAnimation(target, DynamicAnimation.TRANSLATION_Y, 50f)

        // Configure the spring force
        val springForceY = SpringForce(50f)
        springForceY.dampingRatio = SpringForce.DAMPING_RATIO_HIGH_BOUNCY
        springForceY.stiffness = SpringForce.STIFFNESS_LOW

        // Assign the spring force to the animation
        springAnimY.spring = springForceY

        // Add end listener to reset
        springAnimX.addEndListener { _, _, _, _ ->
            springAnimY.addEndListener { _, _, _, _ ->
                target.postDelayed({
                    // Reset to original position
                    target.translationX = 0f
                    target.translationY = 0f

                    // Restart the animations after a delay
                    target.postDelayed({
                        springAnimX.start()
                        springAnimY.start()
                    }, 500)
                }, 1000)
            }
            springAnimY.start()
        }

        // Start the X animation (Y will start when X finishes)
        springAnimX.start()
    }

    // 7. Fling Animation Example
    private fun showFlingAnimationExample() {
        val target = binding.flingAnimationTarget

        // Reset position
        target.translationX = 0f

        // Create a fling animation for translateX
        val flingAnimX = FlingAnimation(target, DynamicAnimation.TRANSLATION_X)

        // Set the friction (higher value = faster slowdown)
        flingAnimX.friction = 1.1f

        // Set the velocity (pixels per second)
        flingAnimX.setStartVelocity(2000f)

        // Optionally, set min and max values
        flingAnimX.setMinValue(0f)
        flingAnimX.setMaxValue(300f)

        // Add end listener
        flingAnimX.addEndListener { _, _, _, _ ->
            // After animation ends, wait a bit and then reset
            target.postDelayed({
                // Create a spring animation to return to start
                val returnAnim = SpringAnimation(target, DynamicAnimation.TRANSLATION_X, 0f)
                val returnSpring = SpringForce(0f)
                returnSpring.dampingRatio = SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY
                returnSpring.stiffness = SpringForce.STIFFNESS_MEDIUM
                returnAnim.spring = returnSpring

                // Add end listener to restart the fling
                returnAnim.addEndListener { _, _, _, _ ->
                    target.postDelayed({
                        flingAnimX.start()
                    }, 500)
                }

                // Start the return animation
                returnAnim.start()
            }, 500)
        }

        // Start the fling animation
        flingAnimX.start()
    }

    // 8. Frame-by-Frame (Drawable) Animation Example
    private fun showFrameByFrameAnimationExample() {
        val target = binding.frameAnimationTarget

        // Create a new AnimationDrawable
        val frameAnimation = AnimationDrawable()

        // In a real app, you would have actual frame images
        // For this example, we'll use system drawables
        val frame1 = ContextCompat.getDrawable(this, android.R.drawable.ic_dialog_email)
        val frame2 = ContextCompat.getDrawable(this, android.R.drawable.ic_dialog_info)
        val frame3 = ContextCompat.getDrawable(this, android.R.drawable.ic_dialog_alert)
        val frame4 = ContextCompat.getDrawable(this, android.R.drawable.ic_dialog_map)

        // Add frames to the animation
        frame1?.let { frameAnimation.addFrame(it, 200) }
        frame2?.let { frameAnimation.addFrame(it, 200) }
        frame3?.let { frameAnimation.addFrame(it, 200) }
        frame4?.let { frameAnimation.addFrame(it, 200) }

        // Set the animation to repeat
        frameAnimation.isOneShot = false

        // Set the animation as the background of the ImageView
        target.setImageDrawable(frameAnimation)

        // Start the animation
        frameAnimation.start()
    }

    // 9. AnimatedVectorDrawable Example
    private fun showAnimatedVectorDrawableExample() {
        val target = binding.vectorAnimationTarget

        // For this example, we'll use a system animated vector drawable
        // In a real app, you would define your own animated vector drawable XML
        val animatedDrawable = AnimatedVectorDrawableCompat.create(
            this,
            android.R.drawable.ic_popup_sync
        )

        target.setImageDrawable(animatedDrawable)

        // Start the animation
        animatedDrawable?.start()

        // Add a repetition mechanism
        target.postDelayed(object : Runnable {
            override fun run() {
                animatedDrawable?.start()
                target.postDelayed(this, 1500)
            }
        }, 1500)
    }

    // 10. Skeleton Loader Example
    private fun showSkeletonLoaderExample() {
        val container = binding.skeletonContainer
        container.removeAllViews()

        // Inflate a skeleton layout
        val skeletonLayout = layoutInflater.inflate(
            R.layout.skeleton_loader,
            container,
            true
        )

        // Start the shimmer effect
        // This is a basic implementation. In a real app, you would use a library
        // like Facebook's Shimmer or implement a custom shimmer effect

        // Simulate loading data
        container.postDelayed({
            // Hide skeleton after "loading" completes
            container.visibility = View.GONE

            // Show actual content (in a real app)
            // actualContentView.visibility = View.VISIBLE
        }, 5000)
    }

    // 11. Shared Element Transition Example
    private fun startSharedElementActivity() {
        // Create a temporary ImageView for demonstration
        val sharedView = ImageView(this).apply {
            id = R.id.shared_element_view
            layoutParams = android.view.ViewGroup.LayoutParams(200, 200)
            setImageResource(android.R.drawable.ic_menu_gallery)
            scaleType = ImageView.ScaleType.CENTER_CROP
            contentDescription = "Shared Element"

            // Add it temporarily to the layout
            binding.root.addView(this)
        }

        // Create an intent for the detail activity
        val intent = Intent(this, SharedElementDetailActivity::class.java)

        // Create the transition options bundle
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
            this,
            Pair(sharedView, "shared_image")
        )

        // Start the detail activity with transition
        startActivity(intent, options.toBundle())
    }
}


//import android.os.Bundle
//import androidx.activity.enableEdgeToEdge
//import androidx.appcompat.app.AppCompatActivity
//import androidx.core.view.ViewCompat
//import androidx.core.view.WindowInsetsCompat
//
//class MainActivity : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContentView(R.layout.activity_main)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
//    }
//}