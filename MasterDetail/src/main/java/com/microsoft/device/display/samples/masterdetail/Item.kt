/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.masterdetail

import java.io.Serializable

class Item private constructor(val title: String, val body: String) : Serializable {
    override fun toString(): String {
        return title
    }

    companion object {
        private const val serialVersionUID = 8383901821872620925L
        // Init items for ListView
        val items: ArrayList<Item>
            get() {
                return ArrayList<Item>().apply {
                    add(Item("Item 1", "This is the first item"))
                    add(Item("Item 2", "This is the second item"))
                    add(Item("Item 3", "This is the third item"))
                }
            }
    }
}