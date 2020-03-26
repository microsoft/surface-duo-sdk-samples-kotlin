/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.masterdetail.model

import java.util.ArrayList

object DataProvider {
    val movieMocks: ArrayList<MovieMock>
        get() {
            val items = ArrayList<MovieMock>()
            items.add(MovieMock("Item 1", "This is the first item"))
            items.add(MovieMock("Item 2", "This is the second item"))
            items.add(MovieMock("Item 3", "This is the third item"))
            return items
        }
}
