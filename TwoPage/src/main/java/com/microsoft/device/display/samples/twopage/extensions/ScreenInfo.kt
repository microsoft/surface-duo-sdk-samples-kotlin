/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.twopage.extensions

import android.view.Surface
import com.microsoft.device.dualscreen.ScreenInfo

/**
 * Returns [true] if the screen is in portrait mode, [false] otherwise
 */
val ScreenInfo.isInPortrait: Boolean
    get() = getScreenRotation() == Surface.ROTATION_90 ||
        getScreenRotation() == Surface.ROTATION_270
