/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.dualview.util

import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Locale

/**
 * @return The rating and voteCount in the following format: "<rating> (<voteCount>)"
 */
fun formatRating(rating: Double, voteCount: Int): String =
    "$rating (${addThousandSeparator(voteCount)})"

/**
 * @return A [String] with the given number of "$" characters
 *
 * @param range The number of "$" characters
 */
fun formatPriceRange(range: Int): String {
    return "$".repeat(range)
}

/**
 * For the input = 2303, the result should be 2,303
 * For the input = 343, the result should be 343
 * For the input = 2303304, the result should be 2,303,304
 */
fun addThousandSeparator(number: Int): String {
    val formatter: DecimalFormat = NumberFormat.getInstance(Locale.US) as DecimalFormat
    formatter.applyPattern("#,###")
    return formatter.format(number)
}
