/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */
package com.microsoft.device.display.samples.contentcontext.fragment

import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {

    interface OnItemSelectedListener {
        fun onItemSelected(position: Int)
    }

    @JvmField
    var listener: OnItemSelectedListener? = null
    fun registerOnItemSelectedListener(listener: OnItemSelectedListener?) {
        this.listener = listener
    }

    abstract fun onBackPressed(): Boolean
    abstract fun setCurrentSelectedPosition(position: Int)
}