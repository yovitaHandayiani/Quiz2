package quiz.navigation

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator

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

    // Corrected mapping with positions as keys and values as values
    private val circleMapping = mapOf(
        0.1f to 30,
        0.3f to 60,
        0.5f to 120,
        0.7f to 240,
        0.9f to 400
    )

    // Animation properties
    private var currProgress = 0f
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
        val paddingHorizontal = width * 0f
        //val paddingHorizontal = width * 0.001f
        //0.0410256
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
        circleMapping.keys.forEach { position ->
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
        circleMapping.entries.forEach { (position, _) ->
            val cx = lineLeft + (lineRight - lineLeft) * position
            // Only draw green circles for positions that have been reached by the progress
            if (position <= progressRatio) {
                canvas.drawCircle(cx, lineY, circleRadius, progressPaint)
            }
        }

        // 5. Draw text values below circles
        val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.BLACK
            textSize = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP,
                11f,  // Use smaller SP value
                resources.displayMetrics
            )
            //Make semibold
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            // For true semibold (if available on the system)
            // typeface = Typeface.create("sans-serif-medium", Typeface.NORMAL)
            textAlign = Paint.Align.CENTER
        }

        // Draw the text values for each circle
        circleMapping.entries.forEach { (position, value) ->
            val cx = lineLeft + (lineRight - lineLeft) * position
            canvas.drawText(value.toString(), cx, lineY + circleRadius * 3, textPaint)
        }
    }

    fun setProgress(progress: Float) {
        currProgress = progress.coerceIn(0f, maxProgress)
        animateToProgress(currProgress)
    }

    private fun animateToProgress(targetProgress: Float) {
        animator?.cancel()

        animator = ValueAnimator.ofFloat(animatedProgress, targetProgress).apply {
            duration = 800
            interpolator = AccelerateDecelerateInterpolator()
            addUpdateListener { animation ->
                animatedProgress = animation.animatedValue as Float
                invalidate()
            }
            start()
        }
    }

    // Update to provide access to the current progress
    fun getCurrentProgress(): Float {
        return animatedProgress
    }

    // Update to provide access to circle positions
    fun getCirclePositions(): FloatArray {
        return circleMapping.keys.toFloatArray()
    }

    // Update to provide access to circle values
    fun getCircleValues(): IntArray {
        return circleMapping.values.toIntArray()
    }

    // Keep this method unchanged
    fun getMaxProgress(): Float {
        return maxProgress
    }

    // Cleanup
    override fun onDetachedFromWindow() {
        animator?.cancel()
        super.onDetachedFromWindow()
    }
}