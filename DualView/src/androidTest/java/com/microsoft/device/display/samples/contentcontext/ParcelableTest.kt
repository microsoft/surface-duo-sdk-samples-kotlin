/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.contentcontext

import android.os.Parcel
import androidx.test.filters.SmallTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.microsoft.device.display.samples.contentcontext.model.Restaurant
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.hamcrest.CoreMatchers.`is` as iz

@RunWith(AndroidJUnit4ClassRunner::class)
@SmallTest
class ParcelableTest {

    @Test
    fun shouldReturnCorrectFields_whenParcelingObject() {
        val mockTitle = "title"
        val mockResId = 5
        val mockRating = 3.3
        val mockVotes = 1435
        val mockType = Restaurant.Type.Thai
        val mockPriceRange = 4
        val mockDescription = "description"
        val item = Restaurant(
            mockTitle,
            mockResId,
            mockRating,
            mockVotes,
            mockType,
            mockPriceRange,
            mockDescription,
            mockResId
        )
        val parcel = Parcel.obtain()
        item.writeToParcel(parcel, 0)
        parcel.setDataPosition(0)

        val createFromParcel = Restaurant.CREATOR.createFromParcel(parcel)

        assertThat(
            "Title field is not parceled correctly",
            mockTitle,
            iz(createFromParcel.title)
        )
        assertThat(
            "ImageResourceId field is not parceled correctly",
            mockResId,
            iz(createFromParcel.imageResourceId)
        )
        assertThat(
            "Rating field is not parceled correctly",
            mockRating,
            iz(createFromParcel.rating)
        )
        assertThat(
            "VoteCount field is not parceled correctly",
            mockVotes,
            iz(createFromParcel.voteCount)
        )
        assertThat(
            "Type field is not parceled correctly",
            mockType,
            iz(createFromParcel.type)
        )
        assertThat(
            "PriceRange field is not parceled correctly",
            mockPriceRange,
            iz(createFromParcel.priceRange)
        )
        assertThat(
            "Description field is not parceled correctly",
            mockDescription,
            iz(createFromParcel.description)
        )
        assertThat(
            "MapImageResourceId field is not parceled correctly",
            mockResId,
            iz(createFromParcel.mapImageResourceId)
        )
    }
}
