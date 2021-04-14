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
import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import com.microsoft.device.display.samples.hingeangle.extensions.hingeAngleSensor

const val DEFAULT_HINGE_ANGLE = 180

/**
 * Value sent by the  [HingeAngleLiveData] when the device doesn't have a hinge.
 */
const val UNAVAILABLE_HINGE = 999

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
                }
            }
        }
    }

    override fun onActive() {
        hingeAngleSensor?.let {
            sensorManager?.registerListener(hingeAngleSensorListener, it, SensorManager.SENSOR_DELAY_NORMAL)
        } ?: run {
            value = UNAVAILABLE_HINGE
        }
    }

    override fun onInactive() {
        super.onInactive()
        hingeAngleSensor?.let {
            sensorManager?.unregisterListener(hingeAngleSensorListener, it)
        }
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
