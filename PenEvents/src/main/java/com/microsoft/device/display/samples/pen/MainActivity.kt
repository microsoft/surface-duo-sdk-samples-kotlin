/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.pen

import android.annotation.SuppressLint
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.SurfaceTexture
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.TextureView.SurfaceTextureListener
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val DEBUG_TAG = "PenEventsDebug"
    private var canDraw = false
    private lateinit var paint: Paint

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        paint = Paint()
        configureTextureView()
    }

    private fun configureTextureView() {
        textureView.surfaceTextureListener = object : SurfaceTextureListener {
            override fun onSurfaceTextureAvailable(surface: SurfaceTexture, width: Int, height: Int) {
                canDraw = true
            }

            override fun onSurfaceTextureSizeChanged(surface: SurfaceTexture, width: Int, height: Int) {}
            override fun onSurfaceTextureDestroyed(surface: SurfaceTexture): Boolean {
                canDraw = false
                return false
            }

            override fun onSurfaceTextureUpdated(surface: SurfaceTexture) {}
        }
    }

    @SuppressLint("DefaultLocale")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        setupViews(event)
        val eventHandled = handleEvent(event)
        drawEvent(event)

        return eventHandled
    }

    private fun setupViews(event: MotionEvent) {
        txtInfo.text = parseToolType(event.getToolType(0))
        txtPressure.text = String.format("%s%s", getString(R.string.Pressure), event.pressure)
        txtOrientation.text = String.format("%s%s", getString(R.string.Orientation), event.orientation)
        txtButton.text = String.format("%s%d", getString(R.string.ButtonState), event.buttonState)
        txtCoords.text = String.format("%s%s, %s", getString(R.string.Location), event.rawX, event.rawY)
        txtTilt.text = String.format("%s%s", getString(R.string.Tilt), event.getAxisValue(MotionEvent.AXIS_TILT))
    }

    private fun parseToolType(toolType: Int): String =
        when (toolType) {
            MotionEvent.TOOL_TYPE_STYLUS -> "Pen "
            MotionEvent.TOOL_TYPE_ERASER -> "Eraser "
            MotionEvent.TOOL_TYPE_FINGER -> "Finger "
            MotionEvent.TOOL_TYPE_MOUSE -> "Mouse "
            else -> "UNKNOWN device $toolType"
        }

    private fun handleEvent(event: MotionEvent): Boolean {
        return when (val action = event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                txtAction.setText(R.string.ActionDown)
                Log.i(DEBUG_TAG, " Action was DOWN ")
                true
            }
            MotionEvent.ACTION_MOVE -> {
                txtAction.setText(R.string.ActionMove)
                Log.i(DEBUG_TAG, " Action was MOVE ")
                true
            }
            MotionEvent.ACTION_UP -> {
                txtAction.setText(R.string.ActionUp)
                Log.i(DEBUG_TAG, " Action was UP ")
                true
            }
            MotionEvent.ACTION_CANCEL -> {
                txtAction.setText(R.string.ActionCancel)
                Log.i(DEBUG_TAG, " Action was CANCEL ")
                true
            }
            MotionEvent.ACTION_HOVER_ENTER -> {
                txtAction.setText(R.string.ActionHoverEnter)
                Log.i(DEBUG_TAG, " Action was HOVER_ENTER")
                true
            }
            MotionEvent.ACTION_HOVER_EXIT -> {
                txtAction.setText(R.string.ActionHoverExit)
                Log.i(DEBUG_TAG, " Action was HOVER_EXIT")
                true
            }
            MotionEvent.ACTION_HOVER_MOVE -> {
                txtAction.setText(R.string.ActionHoverMove)
                Log.i(DEBUG_TAG, " Action was HOVER_MOVE")
                true
            }
            MotionEvent.ACTION_BUTTON_PRESS -> {
                txtAction.setText(R.string.ActionButtonPress)
                Log.i(DEBUG_TAG, " Action was Button Press")
                true
            }
            MotionEvent.ACTION_BUTTON_RELEASE -> {
                txtAction.setText(R.string.ActionButtonRelease)
                Log.i(DEBUG_TAG, " Action was BUTTON_RELEASE")
                true
            }
            else -> {
                Log.i(DEBUG_TAG, "DEFAULT!!! $action")
                super.onTouchEvent(event)
            }
        }
    }

    private fun drawEvent(event: MotionEvent) {
        val canvas: Canvas = textureView.lockCanvas()

        updateBackgroundCanvas(canvas)
        if (unlockCanvasWhenFinishedDrawing(event.action, canvas)) return

        changePaintAsEraserIfApplicable(event.getToolType(0))
        drawPressureCircle(event.buttonState, event.pressure, canvas)
        drawOrientationArc(event.orientation, canvas)
        drawPointUnderPenCircle(event.x, event.y, event.pressure, canvas)

        textureView.unlockCanvasAndPost(canvas)
    }

    private fun updateBackgroundCanvas(canvas: Canvas) {
        paint.style = Paint.Style.FILL
        paint.color = Color.WHITE
        canvas.drawPaint(paint)
    }

    private fun unlockCanvasWhenFinishedDrawing(eventAction: Int, canvas: Canvas): Boolean {
        if (eventAction == MotionEvent.ACTION_UP || eventAction == MotionEvent.ACTION_CANCEL) {
            textureView.unlockCanvasAndPost(canvas)
            return true
        }
        return false
    }

    private fun changePaintAsEraserIfApplicable(toolType: Int) {
        if (toolType == MotionEvent.TOOL_TYPE_ERASER) {
            paint.style = Paint.Style.STROKE
            paint.strokeWidth = 20f
        }
    }

    private fun drawPressureCircle(buttonPressed: Int, pressure: Float, canvas: Canvas) {
        val BOLD_PINK = "#EE00FF"
        val MAGIC_BLUE = "#05A5EC"

        if (buttonPressed != 0) {
            paint.color = Color.parseColor(BOLD_PINK) // If a button is pressed
        } else {
            paint.color = Color.parseColor(MAGIC_BLUE)
        }
        canvas.drawCircle(
            textureView.width / 4.0f,
            textureView.height / 4.0f,
            textureView.height / 4.0f * pressure,
            paint
        )
    }

    private fun drawOrientationArc(eventOrientation: Float, canvas: Canvas) {
        val BOLD_RED = "#CD5C5C"
        val RAD = 57.2958f
        val SWEEP_ANGLE = 5.0f

        val oval = RectF()
        oval.bottom = textureView.height / 2.0f
        oval.top = 0f
        oval.left = 0f
        oval.right = 2 * (textureView.width / 4.0f)

        paint.color = Color.parseColor(BOLD_RED)
        val orientation = (eventOrientation * RAD + 90) % 360
        canvas.drawArc(
            oval,
            orientation,
            SWEEP_ANGLE,
            true,
            paint
        )
    }

    private fun drawPointUnderPenCircle(x: Float, y: Float, pressure: Float, canvas: Canvas) {
        val GRASS_GREEN = "#AA00BB55"

        paint.color = Color.parseColor(GRASS_GREEN)
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
