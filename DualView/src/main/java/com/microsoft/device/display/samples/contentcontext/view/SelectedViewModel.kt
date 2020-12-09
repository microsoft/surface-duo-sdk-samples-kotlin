/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.contentcontext.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.microsoft.device.display.samples.contentcontext.model.DataProvider
import com.microsoft.device.display.samples.contentcontext.model.Restaurant

class SelectedViewModel : ViewModel() {
    val listItems = DataProvider.restaurants
    val selectedPosition: MutableLiveData<Int> = MutableLiveData(-1)

    fun getItem(pos: Int) = listItems.takeIf { it.size > pos && pos >= 0 }?.get(pos)

    fun getItemPosition(item: Restaurant) = listItems.indexOf(item)

    fun setSelectedPosition(pos: Int) {
        if (pos != selectedPosition.value) {
            selectedPosition.value = pos
        }
    }
}
