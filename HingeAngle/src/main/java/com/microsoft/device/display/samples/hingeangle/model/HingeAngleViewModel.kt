/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.hingeangle.model

import android.graphics.Bitmap
import android.graphics.Paint
import android.graphics.Path
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HingeAngleViewModel : ViewModel() {
    val imageLiveData = MutableLiveData<Bitmap?>() // to copy between two screens
    var pathList = listOf<Path>() // to retain the drawing when spanning/unspanning
    var penRadius = 0 // to retain the pen value when spanning/unspanning
    var paints = listOf<Paint>() // to retain the paint value when spanning/unspanning

    fun setImage(image: Bitmap?) {
        imageLiveData.value = image
    }
}
