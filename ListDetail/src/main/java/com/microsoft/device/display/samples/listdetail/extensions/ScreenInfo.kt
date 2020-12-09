/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.listdetail.extensions

import android.view.Surface
import com.microsoft.device.dualscreen.ScreenInfo

val ScreenInfo.isInPortrait: Boolean
    get() = getScreenRotation() == Surface.ROTATION_90 ||
        getScreenRotation() == Surface.ROTATION_270