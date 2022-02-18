/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.multipleinstances.utils

import android.app.Application
import com.microsoft.device.display.samples.multipleinstances.createShortcuts

class TestApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        createShortcuts()
    }
}