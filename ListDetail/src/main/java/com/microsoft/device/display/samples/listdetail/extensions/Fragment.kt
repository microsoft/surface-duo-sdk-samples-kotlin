/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.listdetail.extensions

import androidx.fragment.app.Fragment

/**
 * Returns the [Fragment] class name
 */
val Fragment.TAG: String
    get() {
        return this.javaClass.name
    }