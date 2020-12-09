/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */
package com.microsoft.device.display.samples.listdetail.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SelectionViewModel : ViewModel() {
    private val selectedItemLiveData: MutableLiveData<Int?> = MutableLiveData(0)

    fun getSelectedItemLiveData(): LiveData<Int?> {
        return this.selectedItemLiveData
    }

    fun setSelectedItemLiveData(selectedItem: Int?) {
        selectedItemLiveData.value = selectedItem
    }
}