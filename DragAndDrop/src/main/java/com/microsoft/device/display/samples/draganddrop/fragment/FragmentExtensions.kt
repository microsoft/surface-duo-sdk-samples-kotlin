/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.draganddrop.fragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

/**
 *  The latest added fragment from back stack
 */
val FragmentManager.topFragment: Fragment?
    get() = if (fragments.size > 0) {
        fragments[fragments.size - 1]
    } else {
        null
    }

/**
 * Removes all fragments from back stack.
 */
fun FragmentManager.removeAll() {
    while (fragments.isNotEmpty()) {
        topFragment?.let {
            beginTransaction().remove(it).commitNow()
        }
    }
}