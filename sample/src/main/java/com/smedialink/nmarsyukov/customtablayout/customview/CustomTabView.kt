package com.smedialink.nmarsyukov.customtablayout.customview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.graphics.*
import com.smedialink.nmarsyukov.customtablayout.R

class CustomTabView : View {

    private val defaultTabHeight by lazy { context.resources.getDimension(R.dimen.tab_height) }
    private val defaultTabWidth by lazy { context.resources.getDimension(R.dimen.tab_width) }

    private val contentBounds: RectF by lazy { RectF() }
    private val endTriangleBounds: RectF by lazy { RectF() }

    private val endTrianglePaint by lazy { Paint(Paint.ANTI_ALIAS_FLAG) }
    private val pennantPaint by lazy { Paint(Paint.ANTI_ALIAS_FLAG) }
    private val dividerPaint by lazy { Paint(Paint.ANTI_ALIAS_FLAG) }
    private val textPaint by lazy { Paint(Paint.ANTI_ALIAS_FLAG) }

    var endTriangleFillColor: Int = Color.WHITE
        set(value) {
            field - value
            endTrianglePaint.color = value
            invalidate()
        }

    var tabColor: Int = Color.GREEN
        set(value) {
            field - value
            pennantPaint.color = value
            invalidate()
        }

    var dividerColor: Int = Color.RED
        set(value) {
            field - value
            dividerPaint.color = value
            invalidate()
        }

    var text: String? = null
        set(value) {
            field = value
            invalidate()
        }

    var textSize = 0f
        set(value) {
            field = value
            textPaint.textSize = field
            invalidate()
        }

    var textColor = Color.BLACK
        set(value) {
            field = value
            textPaint.color = field
            invalidate()
        }

    private val endTrianglePath: Path by lazy { Path() }
    private val pennantPath: Path by lazy { Path() }

    private val dividerLinesCoords: FloatArray by lazy { FloatArray(8) }
    private val dividerWidth by lazy { context.resources.getDimension(R.dimen.divider_height) }

    private var textCy = 0f

    constructor(context: Context) : super(context) {
        setupPaints()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        textSize = context.resources.getDimension(R.dimen.font_smallest)

    }

    private fun setupPaints() {
        endTrianglePaint.color = endTriangleFillColor
        pennantPaint.color = tabColor
        dividerPaint.color = dividerColor
        dividerPaint.strokeWidth = dividerWidth

        textSize = context.resources.getDimension(R.dimen.font_smallest)

        textPaint.apply {
            color = textColor
            textAlign = Paint.Align.CENTER
            textSize = this@CustomTabView.textSize
        }

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.apply {
            drawPath(endTrianglePath, endTrianglePaint)
            drawPath(pennantPath, pennantPaint)
            drawLines(dividerLinesCoords, dividerPaint)
            canvas.drawText(text.orEmpty(), contentBounds.centerX(), textCy, textPaint)
        }

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        contentBounds.set(0f, 0f, w.toFloat(), h.toFloat())
        endTriangleBounds.set(w - 30f, 0f, w.toFloat(), h.toFloat())

        pennantPath.moveTo(contentBounds.left, contentBounds.top)
        pennantPath.lineTo(contentBounds.right - 30f - dividerWidth, contentBounds.top)
        pennantPath.lineTo(contentBounds.right - dividerWidth, contentBounds.centerY())
        pennantPath.lineTo(contentBounds.right - 30f - dividerWidth, contentBounds.bottom)
        pennantPath.lineTo(contentBounds.left, contentBounds.bottom)
        pennantPath.lineTo(contentBounds.left, contentBounds.top)

        endTrianglePath.moveTo(endTriangleBounds.left, endTriangleBounds.top)
        endTrianglePath.lineTo(endTriangleBounds.right, endTriangleBounds.centerY())
        endTrianglePath.lineTo(endTriangleBounds.left, endTriangleBounds.bottom)
        endTrianglePath.lineTo(endTriangleBounds.right, endTriangleBounds.bottom)
        endTrianglePath.lineTo(endTriangleBounds.right, endTriangleBounds.top)
        endTrianglePath.close()

        dividerLinesCoords[0] = contentBounds.right - 30f - dividerWidth / 2f
        dividerLinesCoords[1] = contentBounds.top
        dividerLinesCoords[2] = contentBounds.right - dividerWidth / 2f
        dividerLinesCoords[3] = contentBounds.centerY()

        dividerLinesCoords[4] = contentBounds.right - 30f - dividerWidth / 2f
        dividerLinesCoords[5] = contentBounds.bottom
        dividerLinesCoords[6] = contentBounds.right - dividerWidth / 2f
        dividerLinesCoords[7] = contentBounds.centerY()

        val textHeight = textPaint.descent() - textPaint.ascent()
        val textOffset = textHeight / 2 - textPaint.descent()
        //Расчитаем центр текста по оси Y
        textCy = contentBounds.centerY() + textOffset

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var height = calculateHeight(heightMeasureSpec)
        var width = calculateWidth(widthMeasureSpec)
        //Навсякий случай избегаем проблем с предварительным отображением
        width = if (width >= 0) width else 0
        height = if (height >= 0) height else 0
        setMeasuredDimension(width, height)
    }

    private fun calculateHeight(heightMeasureSpec: Int): Int {
        val heightMode = View.MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = View.MeasureSpec.getSize(heightMeasureSpec)
        return when (heightMode) {
            MeasureSpec.EXACTLY -> heightSize //высота жестко задана
            MeasureSpec.AT_MOST -> Math.min(calculateAnticipatedHeight(), heightSize) //высота не жестко задана
            MeasureSpec.UNSPECIFIED -> calculateAnticipatedHeight() //высота не задана
            else -> throw IllegalArgumentException(" incorrect heightMeasureSpec!")
        }
    }

    private fun calculateWidth(widthMeasureSpec: Int): Int {
        val widthMode = View.MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = View.MeasureSpec.getSize(widthMeasureSpec)
        return when (widthMode) {
            View.MeasureSpec.EXACTLY -> widthSize //Ширина жестко задана
            View.MeasureSpec.AT_MOST -> Math.min(calculateAnticipatedWidth(), widthSize) // Ширина не жестко задана
            View.MeasureSpec.UNSPECIFIED -> calculateAnticipatedWidth() // Ширина не задана
            else -> throw IllegalArgumentException(" incorrect widthMeasureSpec!")
        }
    }

    private fun calculateAnticipatedWidth(): Int = defaultTabWidth.toInt()

    private fun calculateAnticipatedHeight(): Int = defaultTabHeight.toInt()

}