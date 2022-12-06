/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.duosamples.about

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LayoutInfoViewModel : ViewModel() {
    var isDualMode = MutableLiveData(false)
}
