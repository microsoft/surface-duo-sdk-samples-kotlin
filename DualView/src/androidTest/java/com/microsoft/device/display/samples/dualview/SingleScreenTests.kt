/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.dualview

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import com.microsoft.device.display.samples.dualview.utils.forceClick
import com.microsoft.device.display.samples.dualview.utils.setOrientationLeft
import com.microsoft.device.display.samples.dualview.utils.setOrientationRight
import com.microsoft.device.display.samples.dualview.utils.unfreezeRotation
import com.microsoft.device.display.samples.dualview.view.RestaurantAdapter
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
@LargeTest
class SingleScreenTests {
    @get:Rule
    val activityRule = ActivityTestRule(DualViewActivity::class.java)

    @After
    fun tearDown() {
        unfreezeRotation()
    }

    @Test
    fun openMapFromList() {
        onView(withId(R.id.first_container_id)).check(matches(isDisplayed()))
        onView(withId(R.id.restaurants_recycler_view)).check(matches(isDisplayed()))

        onView(withId(R.id.restaurants_recycler_view))
            .perform(actionOnItemAtPosition<RestaurantAdapter.RestaurantViewHolder>(3, forceClick()))
        onView(withId(R.id.map_view)).check(matches(isDisplayed()))
    }

    @Test
    fun openMapFromListWithRotationLeft() {
        setOrientationLeft()
        openMapFromList()
    }

    @Test
    fun openMapFromListWithRotationRight() {
        setOrientationRight()
        openMapFromList()
    }
}
