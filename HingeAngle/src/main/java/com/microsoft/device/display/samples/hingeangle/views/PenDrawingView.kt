/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.hingeangle.views

import android.content.Context
import android.graphics.BlurMaskFilter
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewTreeObserver

/**
 * Custom [View] used to draw the finger path on the screen
 */
class PenDrawingView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var currentPath: Path = Path()
    var paintColor: Int = 0

    private var _drawingPathList = mutableListOf<Path>()
    var drawingPathList: List<Path>
        set(value) {
            _drawingPathList = value.toMutableList()
        }
        get() = _drawingPathList

    private var _paints = mutableListOf<Paint>()
    var paints: List<Paint>
        set(value) {
            _paints = value.toMutableList()
        }
        get() = _paints

    var paintRadius = 0
        set(value) {
            field = value
            invalidate()
        }

    private val onDrawListener = ViewTreeObserver.OnDrawListener {
        onDrawAction?.invoke()
    }
    private var onDrawAction: (() -> Unit)? = null

    init {
        paintColor = Color.RED
        setLayerType(LAYER_TYPE_SOFTWARE, null)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        for (index in _drawingPathList.indices) {
            val path = _drawingPathList[index]
            val paint = _paints[index]
            val configuredPaint = configurePaint(paint)
            canvas.drawPath(path, configuredPaint)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                currentPath = Path()
                val paint = Paint().apply {
                    color = paintColor
                    strokeWidth = setStrokeWithPressure(event.pressure)
                }
                _drawingPathList.add(currentPath)
                _paints.add(paint)
                currentPath.moveTo(event.x, event.y)
            }

            MotionEvent.ACTION_MOVE -> {
                currentPath.lineTo(event.x, event.y)
            }
        }
        invalidate()
        return true
    }

    private fun configurePaint(paint: Paint): Paint {
        return Paint().apply {
            val baseValue = 155
            var blurValue = 1
            if (paintRadius > baseValue) {
                blurValue = paintRadius - baseValue
            }
            maskFilter = BlurMaskFilter(blurValue.toFloat(), BlurMaskFilter.Blur.NORMAL)

            val lum = paintRadius / baseValue.toFloat()
            val lumMatrix = ColorMatrix()
            lumMatrix.setScale(lum, lum, lum, 1f)
            val colorMatrixColorFilter = ColorMatrixColorFilter(lumMatrix)
            colorFilter = colorMatrixColorFilter

            style = Paint.Style.STROKE
            isAntiAlias = true
            strokeCap = Paint.Cap.ROUND
            strokeWidth = paint.strokeWidth + paintRadius / 3
            color = paint.color + paintRadius / 4
        }
    }

    private fun setStrokeWithPressure(pressure: Float): Float = pressure * 100

    fun clearDrawingSurface() {
        currentPath = Path()
        _drawingPathList.clear()
        _paints.clear()
        invalidate()
    }

    fun attachOnDrawListener(onDraw: () -> Unit) {
        onDrawAction = onDraw
        viewTreeObserver.addOnDrawListener(onDrawListener)
    }

    fun detachOnDrawListener() {
        viewTreeObserver.removeOnDrawListener(onDrawListener)
    }
}
