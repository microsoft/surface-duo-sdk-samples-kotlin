/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.listdetail.extensions

import androidx.fragment.app.FragmentManager

/**
 * Returns [true] if the fragments back stack is empty, [false] otherwise.
 */
val FragmentManager.isBackStackEmpty: Boolean
    get() {
        return backStackEntryCount == 0
    }