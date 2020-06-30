/*
 *
 *  Copyright (c) Microsoft Corporation. All rights reserved.
 *  Licensed under the MIT License.
 *
 */
package com.microsoft.device.display.samples.widget.service

import android.content.Intent
import android.widget.RemoteViewsService
import com.microsoft.device.display.samples.widget.components.WidgetFactory

class WidgetService : RemoteViewsService() {
    override fun onGetViewFactory(intent: Intent): RemoteViewsFactory {
        return WidgetFactory(this.applicationContext)
    }
}