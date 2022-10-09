/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.dualview.model

import android.graphics.Point

/**
 * Data model class that contains all the restaurant properties
 */
data class Restaurant(
    val title: String? = "",
    val imageResourceId: Int = 0,
    val rating: Double = 0.0,
    val voteCount: Int = 0,
    val type: Type,
    val priceRange: Int = 0,
    val description: String? = "",
    val coordinates: Point
) {

    enum class Type(private val label: String) {
        American("American"),
        Italian("Italian"),
        Thai("Thai"),
        Korean("Korean"),
        FineDine("Fine Dine"),
        Breakfast("Breakfast");

        override fun toString(): String {
            return label
        }
    }
}
