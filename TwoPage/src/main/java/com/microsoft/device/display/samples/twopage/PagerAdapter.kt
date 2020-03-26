/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */
package com.microsoft.device.display.samples.twopage

import android.util.SparseArray
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

internal class PagerAdapter(
    fm: FragmentManager?,
    private val fragments: SparseArray<TestFragment>
) : FragmentPagerAdapter(fm!!, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    private var showTwoPages = false
    override fun getItem(position: Int): Fragment {
        return fragments.valueAt(position)
    }

    override fun getCount(): Int {
        return fragments.size()
    }

    override fun getPageWidth(position: Int): Float {
        // 0.5f : Each pages occupy full space
        // 1.0f : Each pages occupy half space
        return if (showTwoPages) 0.5f else 1.0f
    }

    fun showTwoPages(showTwoPages: Boolean) {
        this.showTwoPages = showTwoPages
    }
}