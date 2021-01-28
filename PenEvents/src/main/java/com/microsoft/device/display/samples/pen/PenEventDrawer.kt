/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.pen

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.view.MotionEvent
import android.view.TextureView
import androidx.annotation.ColorRes

/**
 * Utility class used to draw the pen location, pressure circle and pen orientation arc
 */
class PenEventDrawer(private val textureView: TextureView) {
    companion object {
        const val RADIUS = 57.2958f
        const val SWEEP_ANGLE = 5.0f
    }

    /**
     * The [Paint] used by drawing surface
     */
    private val paint = Paint()
    private val context: Context
        get() = textureView.context

    /**
     * Returns a color associated with a particular resource ID
     *
     * @param id The desired resource identifier
     * @return The corresponding color
     */
    private fun getColor(@ColorRes colorResId: Int): Int = context.getColor(colorResId)

    /**
     * Draw all visual elements (pen location, pressure circle, pen orientation arc) for the given [MotionEvent]
     *
     * @param event The [MotionEvent]
     */
    fun drawEvent(event: MotionEvent) {
        val canvas = textureView.lockCanvas()
        drawBackgroundColor(canvas)
        if (unlockCanvasWhenFinishedDrawing(event.action, canvas)) return

        changePaintAsEraserIfApplicable(event.getToolType(0))
        drawPressureCircle(event.buttonState, event.pressure, canvas)
        drawOrientationArc(event.orientation, canvas)
        drawPointUnderPenCircle(event.x, event.y, event.pressure, canvas)

        textureView.unlockCanvasAndPost(canvas)
    }

    /**
     * Fill the entire [Canvas] with the [Color.WHITE] color
     *
     * @param canvas The [Canvas] used to draw the background
     */
    private fun drawBackgroundColor(canvas: Canvas) {
        paint.style = Paint.Style.FILL
        paint.color = Color.WHITE
        canvas.drawPaint(paint)
    }

    /**
     * Unlock the [Canvas] if the eventAction is [MotionEvent.ACTION_UP] or [MotionEvent.ACTION_CANCEL]
     */
    private fun unlockCanvasWhenFinishedDrawing(eventAction: Int, canvas: Canvas): Boolean {
        return if (eventAction == MotionEvent.ACTION_UP || eventAction == MotionEvent.ACTION_CANCEL) {
            textureView.unlockCanvasAndPost(canvas)
            true
        } else {
            false
        }
    }

    /**
     * Increase the paint strokeWidth if the toolType is [MotionEvent.TOOL_TYPE_ERASER]
     *
     * @param toolType The tool type extracted from [MotionEvent]
     */
    private fun changePaintAsEraserIfApplicable(toolType: Int) {
        if (toolType == MotionEvent.TOOL_TYPE_ERASER) {
            paint.style = Paint.Style.STROKE
            paint.strokeWidth = 20f
        }
    }

    /**
     * Draws the pressure indicator.
     *
     * @param pressure The pen pressure on the screen
     * @param canvas The [Canvas] used to draw the pressure indicator
     */
    private fun drawPressureCircle(buttonPressed: Int, pressure: Float, canvas: Canvas) {
        val colorResId = if (buttonPressed != 0) {
            R.color.bold_pink // If a button is pressed
        } else {
            R.color.magic_blue
        }
        paint.color = getColor(colorResId)

        canvas.drawCircle(
            textureView.width / 4.0f,
            textureView.height / 4.0f,
            textureView.height / 5.0f * pressure,
            paint
        )
    }

    /**
     * Draws the pen orientation arc indicator.
     *
     * @param eventOrientation The pen orientation on the screen
     * @param canvas The [Canvas] used to draw the orientation indicator
     */
    private fun drawOrientationArc(eventOrientation: Float, canvas: Canvas) {
        val oval = RectF()
        oval.bottom = textureView.height / 2.0f
        oval.top = 0f
        oval.left = 0f
        oval.right = 2 * (textureView.width / 4.0f)

        paint.color = getColor(R.color.bold_red)
        val orientation = (eventOrientation * RADIUS + 90) % 360
        canvas.drawArc(oval, orientation, SWEEP_ANGLE, true, paint)
    }

    /**
     * Draws the pen location indicator.
     *
     * @param x The x coordinate on the drawing surface
     * @param y The y coordinate on the drawing surface
     * @param pressure The pen pressure on the screen
     * @param canvas The [Canvas] used to draw the location indicator
     */
    private fun drawPointUnderPenCircle(x: Float, y: Float, pressure: Float, canvas: Canvas) {
        paint.color = getColor(R.color.grass_green)
        val textLocation = IntArray(2)
        textureView.getLocationOnScreen(textLocation)
        canvas.drawCircle(
            x - textLocation[0],
            y - textLocation[1],
            100 * pressure, // The size is controlled by the pressure
            paint
        )
    }
}