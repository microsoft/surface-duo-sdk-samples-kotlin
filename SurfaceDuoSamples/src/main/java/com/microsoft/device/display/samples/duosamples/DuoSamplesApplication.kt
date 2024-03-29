/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.duosamples

import android.app.Application
import com.microsoft.device.display.samples.multipleinstances.createShortcuts
import com.microsoft.device.dualscreen.fragmentshandler.FragmentManagerStateHandler

class DuoSamplesApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        createShortcuts()
        FragmentManagerStateHandler.init(this)
    }
}