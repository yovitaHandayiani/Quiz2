package quiz.navigation

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator

class ShimmerFrameLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {

    private val shimmerPaint = Paint()
    private val shimmerMask = Paint()
    private val gradientColors = intArrayOf(
        Color.argb(50, 255, 255, 255),
        Color.argb(200, 255, 255, 255),
        Color.argb(50, 255, 255, 255)
    )
    private var gradientWidth = 0f
    private var gradientX = 0f
    private val animator = ValueAnimator.ofFloat(-1f, 2f)

    init {
        setWillNotDraw(false)
        shimmerPaint.isAntiAlias = true
        shimmerMask.isAntiAlias = true
        shimmerMask.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)

        animator.duration = 1500
        animator.repeatCount = ValueAnimator.INFINITE
        animator.interpolator = LinearInterpolator()
        animator.addUpdateListener { valueAnimator ->
            gradientX = width * valueAnimator.animatedValue as Float
            invalidate()
        }

        // Start the animation when the view is attached to the window
        addOnAttachStateChangeListener(object : OnAttachStateChangeListener {
            override fun onViewAttachedToWindow(v: View) {
                animator.start()
            }

            override fun onViewDetachedFromWindow(v: View) {
                animator.cancel()
            }
        })
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        gradientWidth = w * 0.5f
    }

    override fun onDraw(canvas: Canvas) {
        // Draw children into an off-screen buffer
        val layer = canvas.saveLayer(0f, 0f, width.toFloat(), height.toFloat(), null)

        // Draw all children normally
        super.onDraw(canvas)

        // Create a gradient that will serve as our shimmer effect
        val gradient = LinearGradient(
            gradientX - gradientWidth, 0f,
            gradientX, 0f,
            gradientColors,
            null,
            Shader.TileMode.CLAMP
        )

        // Apply the gradient to the shimmer paint
        shimmerPaint.shader = gradient

        // Draw the shimmer effect on top of the children
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), shimmerPaint)

        // Restore the canvas
        canvas.restoreToCount(layer)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val width = MeasureSpec.getSize(widthMeasureSpec)
        var height = 0

        // Measure children
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            if (child.visibility != GONE) {
                measureChild(child, widthMeasureSpec, heightMeasureSpec)
                height = maxOf(height, child.measuredHeight)
            }
        }

        setMeasuredDimension(width, height)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        // Layout children
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            if (child.visibility != GONE) {
                child.layout(0, 0, child.measuredWidth, child.measuredHeight)
            }
        }
    }
}