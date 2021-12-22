/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.test.utils

import android.app.Application
import com.microsoft.device.dualscreen.fragmentshandler.FragmentManagerStateHandler

class TestApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        FragmentManagerStateHandler.init(this)
    }
}