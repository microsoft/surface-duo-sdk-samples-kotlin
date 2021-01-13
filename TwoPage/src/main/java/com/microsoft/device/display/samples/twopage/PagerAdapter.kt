/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.twopage

import android.util.SparseArray
import androidx.core.util.forEach
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.microsoft.device.display.samples.twopage.fragments.ScrollingContent

internal class PagerAdapter(
    fragmentManager: FragmentManager,
    private val fragments: SparseArray<Fragment>
) : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    var showTwoPages = false
    var pageContentScrollEnabled = true
        set(value) {
            field = value
            enableScroll(value)
        }

    override fun getItem(position: Int): Fragment {
        return fragments.valueAt(position).apply {
            enableScroll(this, pageContentScrollEnabled)
        }
    }

    override fun getCount(): Int {
        return fragments.size()
    }

    override fun getPageWidth(position: Int): Float {
        // 0.5f : Each pages occupy full space
        // 1.0f : Each pages occupy half space
        return if (showTwoPages) 0.5f else 1.0f
    }

    private fun enableScroll(enabled: Boolean) {
        fragments.forEach { _, fragment -> enableScroll(fragment, enabled) }
    }

    private fun enableScroll(fragment: Fragment, enabled: Boolean) {
        (fragment as? ScrollingContent)?.enableScroll(enabled)
    }
}