/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.contentcontext.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SelectedViewModel : ViewModel() {
    val selectedPosition: MutableLiveData<Int> = MutableLiveData(-1)
}
