/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.complementarycontext.adapter

import android.util.SparseArray
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

import com.microsoft.device.display.samples.complementarycontext.fragment.SlideFragment

class PagerAdapter(fm: FragmentManager, private val fragments: SparseArray<SlideFragment>) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        return fragments.valueAt(position)
    }

    override fun getCount(): Int {
        return fragments.size()
    }
}