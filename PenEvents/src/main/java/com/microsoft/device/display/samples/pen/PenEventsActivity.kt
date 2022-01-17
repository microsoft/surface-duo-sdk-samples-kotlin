/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.pen

import android.os.Bundle
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import com.microsoft.device.display.samples.pen.databinding.ActivityPenEventsBinding

/**
 * Activity containing the drawing surface and some additional info like tool type, location, pressure, etc.
 */
class PenEventsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPenEventsBinding

    private var penEventDrawer: PenEventDrawer? = null
    private var penEventsHandler = PenEventsHandler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPenEventsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        penEventDrawer = PenEventDrawer(binding.textureView)
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
        binding.actionValue.text = getString(penEventsHandler.getActionValue(event))
        if (eventHandled) {
            penEventDrawer?.drawEvent(event)
        }
        return eventHandled
    }

    private fun displayValues(event: MotionEvent) {
        with(binding) {
            deviceInfo.text = parseToolType(event.getToolType(0))
            pressureValue.text =
                getString(R.string.pen_events_pressure_value, event.pressure.toString())
            orientationValue.text =
                getString(R.string.pen_events_orientation_value, event.orientation.toString())
            buttonStateValue.text =
                getString(R.string.pen_events_button_state_value, event.buttonState)
            locationValue.text = getString(
                R.string.pen_events_location_value,
                event.rawX.toString(),
                event.rawY.toString()
            )
            tiltValue.text = getString(
                R.string.pen_events_tilt_value,
                event.getAxisValue(MotionEvent.AXIS_TILT).toString()
            )
        }
    }
}
