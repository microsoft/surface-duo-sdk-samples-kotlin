/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.twopage.utils

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice

/**
 * Horizontal swipe from right to left
 */
fun horizontalSwipeToLeft() {
    val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
    device.swipe(1500, 2000, 200, 2000, 10)
}

/**
 * Vertical swipe from bottom to top
 */
fun verticalSwipeToTop() {
    val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
    device.swipe(1000, 1000, 1000, 200, 10)
}