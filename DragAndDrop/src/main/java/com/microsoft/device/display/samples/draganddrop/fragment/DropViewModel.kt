package com.microsoft.device.display.samples.draganddrop.fragment

import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DropViewModel : ViewModel() {
    var text = MutableLiveData<CharSequence>("")
    var image = MutableLiveData<Bitmap?>(null)
}