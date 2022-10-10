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
    var pathList = listOf<Path>() // to retain the drawing when spanning/un-spanning
    var penRadius = 0 // to retain the pen value when spanning/un-spanning
    var paints = listOf<Paint>() // to retain the paint value when spanning/un-spanning
    var selectedPen = 0 // to retain the selected pen when spanning/un-spanning

    fun setImage(image: Bitmap?) {
        imageLiveData.value = image
    }
}
