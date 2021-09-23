/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.hingeangle.extensions

import android.hardware.Sensor
import android.hardware.SensorManager

private const val A10_HINGE_ANGLE_SENSOR_NAME = "Hinge Angle"
private const val A11_HINGE_ANGLE_SENSOR_NAME = "Goldfish hinge sensor"

/**
 * Finds and returns the hinge angle sensor, or [null] if doesn't exists
 */
val SensorManager?.hingeAngleSensor: Sensor?
    get() = this?.getSensorList(Sensor.TYPE_ALL)?.firstOrNull {
        it.name.contains(A10_HINGE_ANGLE_SENSOR_NAME) ||
            it.name.contains(A11_HINGE_ANGLE_SENSOR_NAME)
    }
