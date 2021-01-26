/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.hingeangle.extensions

import android.hardware.Sensor
import android.hardware.SensorManager

private const val HINGE_ANGLE_SENSOR_NAME = "Hinge Angle"

/**
 * Finds and returns the hinge angle sensor, or [null] if doesn't exists
 */
val SensorManager?.hingeAngleSensor: Sensor?
    get() = this?.getSensorList(Sensor.TYPE_ALL)?.firstOrNull {
        it.name.contains(HINGE_ANGLE_SENSOR_NAME)
    }