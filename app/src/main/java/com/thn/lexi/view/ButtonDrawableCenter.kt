package com.thn.lexi.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.support.v7.widget.AppCompatButton
import android.util.AttributeSet

/**
 * 图片和文字居中的按钮
 */
class ButtonDrawableCenter : AppCompatButton {

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}


    override fun onDraw(canvas: Canvas) {
        val drawables = compoundDrawables
        if (drawables != null) {
            val drawableLeft = drawables[0]
            val drawableRight = drawables[2]

            if (drawableLeft != null) {
                setDrawableCenter(drawableLeft, canvas)
            } else if (drawableRight != null) {
                setDrawableCenter(drawableRight, canvas)
            }

        }
        super.onDraw(canvas)
    }

    private fun setDrawableCenter(drawableRight: Drawable, canvas: Canvas) {
        val textWidth = paint.measureText(text.toString())
        val drawablePadding = compoundDrawablePadding
        val drawableWidth = drawableRight.intrinsicWidth
        val bodyWidth = textWidth + drawableWidth.toFloat() + drawablePadding.toFloat()
        setPadding(0, 0, (width - bodyWidth).toInt(), 0)
        canvas.translate((width - bodyWidth) / 2, 0f)
    }
}
