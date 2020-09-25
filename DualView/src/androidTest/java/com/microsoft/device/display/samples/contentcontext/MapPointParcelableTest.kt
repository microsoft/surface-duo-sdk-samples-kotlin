/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.contentcontext

import android.os.Parcel
import androidx.test.filters.SmallTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.microsoft.device.display.samples.contentcontext.model.MapPoint
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.hamcrest.CoreMatchers.`is` as iz

@RunWith(AndroidJUnit4ClassRunner::class)
@SmallTest
class MapPointParcelableTest {

    @Test
    fun shouldReturnCorrectFields_whenParcelingObject() {
        val mockTitle = "title"
        val mockResId = 5
        val mapPoint = MapPoint(mockTitle, mockResId)
        val parcel = Parcel.obtain()
        mapPoint.writeToParcel(parcel, 0)
        parcel.setDataPosition(0)

        val createFromParcel = MapPoint.CREATOR.createFromParcel(parcel)

        assertThat(
            "Title field is not parceled correctly",
            mockTitle,
            iz(createFromParcel.title)
        )
        assertThat(
            "MapImageResourceID field is not parceled correctly",
            mockResId,
            iz(createFromParcel.mapImageResourceID)
        )
    }
}
