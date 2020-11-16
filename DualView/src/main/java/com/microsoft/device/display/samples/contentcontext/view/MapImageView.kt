/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.contentcontext.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Matrix
import android.graphics.PointF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import kotlin.math.sqrt

class MapImageView : AppCompatImageView {
    companion object {
        internal const val NONE = 0
        internal const val DRAG = 1
        internal const val ZOOM = 2
        internal const val DISTANCE_UNTIL_WATERMARK = 400
        internal const val SCALE_FACTOR = 0.8f
    }

    internal var matrix = Matrix()
    internal var savedMatrix = Matrix()
    internal var mode = NONE
    internal var start = PointF()
    internal var mid = PointF()
    internal var oldDist = 1f

    @SuppressLint("ClickableViewAccessibility")
    constructor(context: Context) : super(context) {
        setupView()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        setupView()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        setupView()
    }

    private fun setupView() {
        setOnTouchListener(MyTouch())
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        drawable?.let {
            val x = (width - drawable.intrinsicWidth.toFloat() * SCALE_FACTOR) / 2
            val y = (height - drawable.intrinsicHeight.toFloat() * SCALE_FACTOR) / 2 -
                DISTANCE_UNTIL_WATERMARK
            matrix.preScale(SCALE_FACTOR, SCALE_FACTOR)
            matrix.preTranslate(x, y)
            this.imageMatrix = matrix
        }
    }

    internal inner class MyTouch : OnTouchListener {
        @SuppressLint("ClickableViewAccessibility")
        override fun onTouch(v: View, event: MotionEvent): Boolean {
            val mImageView = v as ImageView
            mImageView.scaleType = ScaleType.MATRIX
            val scale: Float

            when (event.action and MotionEvent.ACTION_MASK) {
                MotionEvent.ACTION_DOWN -> {
                    savedMatrix.set(matrix)
                    start.set(event.x, event.y)
                    mode = DRAG
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_POINTER_UP -> {
                    mode = NONE
                }
                MotionEvent.ACTION_POINTER_DOWN -> {
                    oldDist = spacing(event)
                    if (oldDist > 5f) {
                        savedMatrix.set(matrix)
                        midPoint(mid, event)
                        mode = ZOOM
                    }
                }
                MotionEvent.ACTION_MOVE ->
                    if (mode == DRAG) {
                        matrix.set(savedMatrix)
                        if (mImageView.left >= -392) {
                            matrix.postTranslate(event.x - start.x, event.y - start.y)
                        }
                    } else if (mode == ZOOM) {
                        val newDist = spacing(event)
                        if (newDist > 5f) {
                            matrix.set(savedMatrix)
                            scale = newDist / oldDist
                            matrix.postScale(scale, scale, mid.x, mid.y)
                        }
                    }
            }
            mImageView.imageMatrix = matrix
            return true
        }

        private fun spacing(event: MotionEvent): Float {
            val x = event.getX(0) - event.getX(1)
            val y = event.getY(0) - event.getY(1)
            return sqrt((x * x + y * y).toDouble()).toFloat()
        }

        private fun midPoint(point: PointF, event: MotionEvent) {
            val x = event.getX(0) + event.getX(1)
            val y = event.getY(0) + event.getY(1)
            point.set(x / 2, y / 2)
        }
    }
}
