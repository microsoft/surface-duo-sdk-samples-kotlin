/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.listdetail.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.microsoft.device.display.samples.listdetail.R

/**
 * ViewModel used to store the current selected image
 */
class SelectionViewModel : ViewModel() {
    val selectedItem = MutableLiveData(R.drawable.list_details_image_1)
}