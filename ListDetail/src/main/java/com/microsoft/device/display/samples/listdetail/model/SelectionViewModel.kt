package com.microsoft.device.display.samples.listdetail.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SelectionViewModel : ViewModel() {
    val selectedPosition: MutableLiveData<Int> = MutableLiveData(-1)
}