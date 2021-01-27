/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.hingeangle.model

import android.app.Service
import android.content.Context
import android.hardware.SensorEvent
import android.hardware.SensorManager
import android.util.Log
import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import com.microsoft.device.display.samples.hingeangle.extensions.hingeAngleSensor

private const val DEFAULT_HINGE_ANGLE = 180

/**
 * LiveData that exposes the hinge angle values from sensor
 */
class HingeAngleLiveData(context: Context) : LiveData<Int>() {
    private val sensorManager = context.getSystemService(Service.SENSOR_SERVICE) as? SensorManager
    private val hingeAngleSensor = sensorManager?.hingeAngleSensor

    init {
        value = DEFAULT_HINGE_ANGLE
    }

    private val hingeAngleSensorListener = object : SensorEventListenerAdapter() {
        override fun onSensorChanged(event: SensorEvent?) {
            event?.let {
                if (it.sensor == hingeAngleSensor) {
                    val angle = it.values[0].toInt()
                    value = angle
                    Log.e("HingeAngleLiveData", "New Hinge Angle1:  , " + it.values[0] +
                        "--2:  " + it.values[1] +
                        "--3:  " + it.values[2] +
                        "-- avg:  " + (it.values[0] + it.values[1] + it.values[2]) / 3
                    )
                }
            }
        }
    }

    override fun onActive() {
        hingeAngleSensor?.let {
            sensorManager?.registerListener(hingeAngleSensorListener, it, SensorManager.SENSOR_DELAY_NORMAL)
        } ?: throw IllegalStateException("HingeAngleSensor cannot be null")
    }

    override fun onInactive() {
        super.onInactive()
        hingeAngleSensor?.let {
            sensorManager?.unregisterListener(hingeAngleSensorListener, it)
        } ?: throw IllegalStateException("HingeAngleSensor cannot be null")
    }

    companion object {
        private lateinit var sInstance: HingeAngleLiveData

        @MainThread
        fun get(context: Context): HingeAngleLiveData {
            sInstance = if (::sInstance.isInitialized) sInstance else HingeAngleLiveData(context)
            return sInstance
        }
    }
}
