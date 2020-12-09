/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.listdetail

import android.app.Application
import com.microsoft.device.dualscreen.ScreenManagerProvider
import com.microsoft.device.dualscreen.fragmentshandler.FragmentManagerStateHandler

class ListDetailApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        ScreenManagerProvider.init(this)
        FragmentManagerStateHandler.init(this)
    }
}