package com.microsoft.device.display.samples.complementarycontext.fragment

import androidx.fragment.app.Fragment

open class BaseFragment : Fragment() {

    interface OnItemSelectedListener {
        fun onItemSelected(position: Int)
    }

    @JvmField
    var listener: OnItemSelectedListener? = null
    fun registerOnItemSelectedListener(listener: OnItemSelectedListener?) {
        this.listener = listener
    }
}
