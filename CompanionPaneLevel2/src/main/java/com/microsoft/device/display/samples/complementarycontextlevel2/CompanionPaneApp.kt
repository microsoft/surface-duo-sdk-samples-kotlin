/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.complementarycontextlevel2

import android.app.Application
import com.microsoft.device.dualscreen.layout.SurfaceDuoScreenManager

class CompanionPaneApp : Application() {
    override fun onCreate() {
        super.onCreate()
        SurfaceDuoScreenManager.init(this)
    }
}
