/*
 *
 *  * Copyright (c) Microsoft Corporation. All rights reserved.
 *  * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.hingeangle.model

import android.graphics.Bitmap
import android.graphics.Paint
import android.graphics.Path
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ViewModel : ViewModel() {
    private val imageLiveData = MutableLiveData<Bitmap?>() // to copy between two screens
    private var pathList = listOf<Path>() // to retain the drawing when spanning/unspanning
    private var penRadius: Int = 0 // to retain the pen value when spanning/unspanning
    private var paints = listOf<Paint>() // to retain the paint value when spanning/unspanning
    private val hingeAngleLiveData = MutableLiveData<Int>() // observe the hinge angle change

    fun getImageLiveData(): LiveData<Bitmap?> {
        return this.imageLiveData
    }

    fun setImageLiveData(bitmap: Bitmap?) {
        imageLiveData.value = bitmap
    }

    fun getPathList(): List<Path> {
        return this.pathList
    }

    fun setPathList(pathList: List<Path>) {
        this.pathList = pathList
    }

    fun getPenRadius(): Int {
        return penRadius
    }

    fun setPenRadius(radius: Int) {
        penRadius = radius
    }

    fun getPaints(): List<Paint> {
        return this.paints
    }

    fun setPaints(paints: List<Paint>) {
        this.paints = paints
    }

    fun getHingeAngleLiveData(): LiveData<Int> {
        return this.hingeAngleLiveData
    }

    fun setHingeAngleLiveData(hingeAngle: Int) {
        hingeAngleLiveData.value = hingeAngle
    }
}