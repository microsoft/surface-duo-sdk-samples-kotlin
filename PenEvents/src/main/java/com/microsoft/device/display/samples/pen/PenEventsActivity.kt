/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.pen

import android.os.Bundle
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.pen_events_activity_main.*

/**
 * Activity containing the drawing surface and some additional info like tool type, location, pressure, etc.
 */
class PenEventsActivity : AppCompatActivity() {
    private var penEventDrawer: PenEventDrawer? = null
    private var penEventsHandler = PenEventsHandler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pen_events_activity_main)
        setupToolbar()
        penEventDrawer = PenEventDrawer(textureView)
        penEventsHandler = PenEventsHandler()
    }

    private fun setupToolbar() {
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        displayValues(event)
        val eventHandled = penEventsHandler.canHandleEvent(event)
        action_value.text = getString(penEventsHandler.getActionValue(event))
        if (eventHandled) {
            penEventDrawer?.drawEvent(event)
        }
        return eventHandled
    }

    private fun displayValues(event: MotionEvent) {
        device_info.text = parseToolType(event.getToolType(0))
        pressure_value.text = getString(R.string.pen_events_pressure_value, event.pressure.toString())
        orientation_value.text = getString(R.string.pen_events_orientation_value, event.orientation.toString())
        button_state_value.text = getString(R.string.pen_events_button_state_value, event.buttonState)
        location_value.text = getString(R.string.pen_events_location_value, event.rawX.toString(), event.rawY.toString())
        tilt_value.text = getString(R.string.pen_events_tilt_value, event.getAxisValue(MotionEvent.AXIS_TILT).toString())
    }
}
