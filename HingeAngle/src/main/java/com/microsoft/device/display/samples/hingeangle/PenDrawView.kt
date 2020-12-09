/*
 * Copyright (c) Microsoft Corporation. All rights reserved.Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.hingeangle

import android.content.Context
import android.graphics.Bitmap
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

class PenDrawView : View {

    private var currentPath: Path = Path()
    private var currentColor: Int = 0
    private var drawBitmap: Bitmap? = null
    private var pathList: MutableList<Path> = mutableListOf()
    private var paints: MutableList<Paint> = mutableListOf()
    private var radius = 0

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init()
    }

    private fun init() {
        currentColor = Color.RED
        setLayerType(LAYER_TYPE_SOFTWARE, null)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (this.pathList.isNotEmpty()) {
            for (index in 0 until this.pathList.size) {
                val path = this.pathList[index]
                val paint = this.paints[index]
                val configuredPaint = configurePaint(paint)
                canvas.drawPath(path, configuredPaint)
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                currentPath = Path()
                val paint = Paint()
                paint.color = currentColor
                paint.strokeWidth = setStrokeWithPressure(event.pressure)
                pathList.add(currentPath)
                paints.add(paint)
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
        val configuredPaint = Paint()
        val baseValue = 155
        var blurValue = 1
        if (radius > baseValue) {
            blurValue = radius - baseValue
        }
        configuredPaint.maskFilter = BlurMaskFilter(blurValue.toFloat(), BlurMaskFilter.Blur.NORMAL)

        val lum = radius / baseValue.toFloat()
        val lumMatrix = ColorMatrix()
        lumMatrix.setScale(lum, lum, lum, 1f)
        val colorMatrixColorFilter = ColorMatrixColorFilter(lumMatrix)
        configuredPaint.colorFilter = colorMatrixColorFilter

        configuredPaint.style = Paint.Style.STROKE
        configuredPaint.isAntiAlias = true
        configuredPaint.strokeCap = Paint.Cap.ROUND
        configuredPaint.strokeWidth = paint.strokeWidth + radius / 3
        configuredPaint.color = paint.color + radius / 4
        return configuredPaint
    }

    private fun setStrokeWithPressure(pressure: Float): Float {
        return pressure * 100
    }

    fun setPaintRadius(radius: Int) {
        this.radius = radius
        this.invalidate()
    }

    fun getPaints(): List<Paint> {
        return this.paints
    }

    fun setPaints(paints: List<Paint>) {
        this.paints = paints.toMutableList()
    }

    fun changePaintColor(color: Int) {
        this.currentColor = color
    }

    fun getDrawBitmap(): Bitmap? {
        saveDrawBitmap()
        return this.drawBitmap
    }

    fun getDrawPathList(): List<Path> {
        return this.pathList
    }

    fun setDrawPathList(list: List<Path>) {
        this.pathList = list.toMutableList()
    }

    fun clearDrawing() {
        this.drawBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        this.currentPath = Path()
        this.pathList.clear()
        this.paints.clear()

        invalidate()
    }

    private fun saveDrawBitmap() {
        if (height > 0 && width > 0) {
            this.drawBitmap = takeScreenshotOfView(this, height, width)
        }
    }

    private fun takeScreenshotOfView(view: View, height: Int, width: Int): Bitmap {
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.draw(canvas)

        return bitmap
    }
}
