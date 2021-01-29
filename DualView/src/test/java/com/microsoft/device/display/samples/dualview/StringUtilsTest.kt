/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.dualview

import com.microsoft.device.display.samples.dualview.util.formatPriceRange
import com.microsoft.device.display.samples.dualview.util.formatRating
import org.junit.Assert.assertEquals
import org.junit.Test

class StringUtilsTest {

    @Test
    fun testFormatRating() {
        assertEquals(
            "formatRating() should return both rating and vote count",
            "3.3 (1,804)",
            formatRating(3.3, 1804)
        )
        assertEquals(
            "formatRating() should not include comma when vote count is smaller than 1000",
            "3.3 (300)",
            formatRating(3.3, 300)
        )
        assertEquals(
            "formatRating() should correctly format 5-digit vote count number",
            "2.0 (23,000)",
            formatRating(2.0, 23000)
        )
    }

    @Test
    fun testFormatPriceRange() {
        assertEquals(
            "formatPriceRange() should return 3 dollars when value is 3",
            "$$$",
            formatPriceRange(3)
        )
        assertEquals(
            "formatPriceRange() should return 1 dollar when value is 1",
            "$",
            formatPriceRange(1)
        )
        assertEquals(
            "formatPriceRange() should return empty string when value is 0",
            "",
            formatPriceRange(0)
        )
    }
}
