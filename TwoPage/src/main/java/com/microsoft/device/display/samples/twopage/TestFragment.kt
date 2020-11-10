/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */
package com.microsoft.device.display.samples.twopage

import android.util.SparseArray
import androidx.fragment.app.Fragment

class TestFragment : Fragment() {
    companion object {
        // Init fragments for ViewPager
        val fragments: SparseArray<Fragment>
            get() {
                val fragments = SparseArray<Fragment>()
                fragments.put(0, FirstPageFragment())
                fragments.put(1, SecondPageFragment())
                fragments.put(2, ThirdPageFragment())
                fragments.put(3, FourthPageFragment())
                return fragments
            }
    }
}