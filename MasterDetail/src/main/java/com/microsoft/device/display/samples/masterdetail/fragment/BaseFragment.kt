/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.masterdetail.fragment

import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {
    abstract fun onBackPressed(): Boolean
    abstract var currentSelectedPosition: Int
}
