package com.example.kotlin.compass_view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat


class CompassView : View {

    private var rotationInRadians  = 0.toFloat()
    private var paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var linePaint = Paint(Paint.ANTI_ALIAS_FLAG)

    constructor(context: Context) : super(context){
        setCirclePaint()
        setLinePaint()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs){
        setCirclePaint()
        setLinePaint()
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle){
        setCirclePaint()
        setLinePaint()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightMeasureSpec))
    }

    private fun setCirclePaint() {
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 5f
        paint.color = Color.GRAY
    }

    private fun setLinePaint() {
        linePaint.color = ContextCompat.getColor(context, android.R.color.holo_red_dark)
    }

    override fun onDraw(canvas: Canvas) {

        val width = measuredWidth
        val height = measuredHeight
        val radius: Int

        radius = if (width > height) {
            height / 2
        } else {
            width / 2
        }

        canvas.drawCircle((width / 2).toFloat(), (height / 2).toFloat(), radius.toFloat(), paint)

        canvas.drawLine(
            (width / 2).toFloat(),
            (height / 2).toFloat(),
            (width / 2 + radius * Math.sin((-rotationInRadians).toDouble())).toFloat(),
            (height / 2 - radius * Math.cos((-rotationInRadians).toDouble())).toFloat(),
            linePaint
        )

    }

    fun update(rotationInRadians: Float) {
        this.rotationInRadians = rotationInRadians

        //Call onDraw to update compass
        invalidate()
    }

}