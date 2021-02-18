/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.dualview.view

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

/**
 * Fake map view implementation
 */
class MapImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {
    companion object {
        internal const val NONE = 0
        internal const val DRAG = 1
        internal const val ZOOM = 2
        internal const val DISTANCE_UNTIL_WATERMARK = 1000
        internal const val SCALE_FACTOR = 0.5f
    }

    internal var matrix = Matrix()
    internal var savedMatrix = Matrix()
    internal var mode = NONE
    internal var start = PointF()
    internal var mid = PointF()
    internal var oldDist = 1f

    init {
        setOnTouchListener(MapViewOnTouchListener())
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)

        drawable?.let {
            val x = (width - drawable.intrinsicWidth.toFloat() * SCALE_FACTOR) / 2 -
                DISTANCE_UNTIL_WATERMARK
            val y = (height - drawable.intrinsicHeight.toFloat() * SCALE_FACTOR) / 2 -
                DISTANCE_UNTIL_WATERMARK * 2
            matrix.preScale(SCALE_FACTOR, SCALE_FACTOR)
            matrix.preTranslate(x, y)
            imageMatrix = matrix
        }
    }

    internal inner class MapViewOnTouchListener : OnTouchListener {
        @SuppressLint("ClickableViewAccessibility")
        override fun onTouch(view: View, event: MotionEvent): Boolean {
            val imageView = view as ImageView
            imageView.scaleType = ScaleType.MATRIX
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
                        if (imageView.left >= -392) {
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
            imageView.imageMatrix = matrix
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
