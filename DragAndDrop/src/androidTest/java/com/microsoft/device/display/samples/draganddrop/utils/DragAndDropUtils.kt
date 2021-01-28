/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.draganddrop.utils

import android.view.Surface
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice

fun dragAndDropImageInSingleScreenMode() {
    val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
    when (device.displayRotation) {
        Surface.ROTATION_0 -> device.swipe(300, 600, 300, 1300, 400)
        Surface.ROTATION_270 -> device.swipe(450, 450, 450, 1050, 400)
        Surface.ROTATION_90 -> device.swipe(450, 1850, 450, 2450, 400)
    }
}

fun dragAndDropTextInSingleScreenMode() {
    val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
    when (device.displayRotation) {
        Surface.ROTATION_0 -> device.swipe(1000, 600, 1000, 1300, 400)
        Surface.ROTATION_270 -> device.swipe(1320, 470, 1350, 1050, 400)
        Surface.ROTATION_90 -> device.swipe(1320, 1850, 1320, 2450, 400)
    }
}

fun dragAndDropImageInDualScreenMode() {
    val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
    when (device.displayRotation) {
        Surface.ROTATION_0 -> device.swipe(300, 1000, 1800, 1000, 400)
        Surface.ROTATION_90,
        Surface.ROTATION_270 -> device.swipe(450, 780, 450, 2100, 400)
    }
}

fun dragAndDropTextInDualScreenMode() {
    val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
    when (device.displayRotation) {
        Surface.ROTATION_0 -> device.swipe(900, 900, 2500, 900, 400)
        Surface.ROTATION_90,
        Surface.ROTATION_270 -> device.swipe(1320, 780, 1320, 2100, 400)
    }
}
