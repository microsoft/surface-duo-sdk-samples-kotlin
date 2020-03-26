/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.masterdetail.model

import java.io.Serializable

class MovieMock internal constructor(val title: String, val body: String) : Serializable {

    companion object {
        const val KEY = "MovieMock"
    }

    override fun toString(): String {
        return title
    }
}
