/*
 *
 *  * Copyright (c) Microsoft Corporation. All rights reserved.
 *  * Licensed under the MIT License.
 *  *
 *
 */

package com.microsoft.device.display.samples.duosamples

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.microsoft.device.display.samples.duosamples.samples.SampleModel

class SampleViewModel : ViewModel() {
    val selectedSample = MutableLiveData<SampleModel?>(null)
}