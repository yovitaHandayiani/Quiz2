package quiz.navigation

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.RectF
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.view.animation.LinearInterpolator

class CustomProgressBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    // Colors
    private val grayColor = Color.parseColor("#D8D8D8")
    private val greenColor = Color.parseColor("#31734E")

    // Paint objects
    private val backgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = grayColor
        style = Paint.Style.FILL
    }

    private val progressPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = greenColor
        style = Paint.Style.FILL
    }

    // Dimensions (convert dp to pixels)
    private val circleRadius = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, 6f, resources.displayMetrics)
    private val lineHeight = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, 3f, resources.displayMetrics)

    // Updated circle positions as requested
    private val circlePositions = arrayOf(0.1f, 0.3f, 0.5f, 0.7f, 0.9f)
    private val circleValues = arrayOf(30, 60, 120, 240, 400)

    // Animation properties
    private var currentProgress = 0f
    private var maxProgress = 100f
    private var animator: ValueAnimator? = null
    private var animatedProgress = 0f

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val width = width.toFloat()
        val height = height.toFloat()
        val paddingHorizontal = width * 0.05f
        val lineLeft = paddingHorizontal
        val lineRight = width - paddingHorizontal
        val lineY = height / 2f

        // Calculate progress width
        val progressWidth = (lineRight - lineLeft) * (animatedProgress / maxProgress)
        val progressEndX = lineLeft + progressWidth

        // 1. Draw the background line (gray)
        val backgroundLineRect = RectF(lineLeft, lineY - lineHeight, lineRight, lineY + lineHeight)
        canvas.drawRect(backgroundLineRect, backgroundPaint)

        // 2. Draw all the background circles (gray)
        circlePositions.forEach { position ->
            val cx = lineLeft + (lineRight - lineLeft) * position
            canvas.drawCircle(cx, lineY, circleRadius, backgroundPaint)
        }

        // 3. Draw the progress line (green)
        if (progressEndX > lineLeft) {
            val progressLineRect = RectF(lineLeft, lineY - lineHeight, progressEndX, lineY + lineHeight)
            canvas.drawRect(progressLineRect, progressPaint)
        }

        // 4. Draw the progress circles (green)
        val progressRatio = animatedProgress / maxProgress
        circlePositions.forEachIndexed { index, position ->
            val cx = lineLeft + (lineRight - lineLeft) * position
            // Only draw green circles for positions that have been reached by the progress
            if (position <= progressRatio) {
                canvas.drawCircle(cx, lineY, circleRadius, progressPaint)
            }
        }

        // 5. Draw text values below circles
        val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.BLACK
            textSize = 24f
            textAlign = Paint.Align.CENTER
        }

        // Draw the text values for each circle
        circlePositions.forEachIndexed { index, position ->
            val cx = lineLeft + (lineRight - lineLeft) * position
            canvas.drawText(circleValues[index].toString(), cx, lineY + circleRadius * 3, textPaint)
        }
    }

    fun setProgress(progress: Float) {
        currentProgress = progress.coerceIn(0f, maxProgress)
        animateToProgress(currentProgress)
    }

    private fun animateToProgress(targetProgress: Float) {
        animator?.cancel()

        animator = ValueAnimator.ofFloat(animatedProgress, targetProgress).apply {
            duration = 1000
            interpolator = LinearInterpolator()
            addUpdateListener { animation ->
                animatedProgress = animation.animatedValue as Float
                invalidate()
            }
            start()
        }
    }

    // Add this method to provide access to the current progress
    fun getCurrentProgress(): Float {
        return animatedProgress
    }

    // Add this method to provide access to circlePositions
    fun getCirclePositions(): FloatArray {
        return circlePositions.toFloatArray()
    }

    // Add this method to provide access to maxProgress
    fun getMaxProgress(): Float {
        return maxProgress
    }

    // Cleanup
    override fun onDetachedFromWindow() {
        animator?.cancel()
        super.onDetachedFromWindow()
    }
}