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
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import com.microsoft.device.display.samples.dualview.view.RestaurantAdapter
import com.microsoft.device.display.samples.test.utils.forceClick
import com.microsoft.device.dualscreen.testing.resetOrientation
import com.microsoft.device.dualscreen.testing.spanFromStart
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
@LargeTest
class DualScreenTests {
    @get:Rule
    val activityScenarioRule = activityScenarioRule<DualViewActivity>()
    private val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

    @After
    fun tearDown() {
        device.resetOrientation()
    }

    @Test
    fun displayListAndMapFromList() {
        device.spanFromStart()

        onView(withId(R.id.first_container_id)).check(matches(isDisplayed()))
        onView(withId(R.id.second_container_id)).check(matches(isDisplayed()))

        onView(withId(R.id.restaurants_recycler_view)).check(matches(isDisplayed()))
        onView(withId(R.id.map_view)).check(matches(isDisplayed()))
    }

    @Test
    fun displayListAndMapFromListWithRotationLeft() {
        device.setOrientationLeft()
        displayListAndMapFromList()
    }

    @Test
    fun displayListAndMapFromListWithRotationRight() {
        device.setOrientationRight()
        displayListAndMapFromList()
    }

    @Test
    fun displayListAndMapFromMap() {
        onView(withId(R.id.first_container_id)).check(matches(isDisplayed()))
        onView(withId(R.id.restaurants_recycler_view)).check(matches(isDisplayed()))

        onView(withId(R.id.restaurants_recycler_view))
            .perform(actionOnItemAtPosition<RestaurantAdapter.RestaurantViewHolder>(3, forceClick()))

        device.spanFromStart()

        onView(withId(R.id.first_container_id)).check(matches(isDisplayed()))
        onView(withId(R.id.second_container_id)).check(matches(isDisplayed()))

        onView(withId(R.id.restaurants_recycler_view)).check(matches(isDisplayed()))
        onView(withId(R.id.map_view)).check(matches(isDisplayed()))
    }

    @Test
    fun displayListAndMapFromMapWithRotationLeft() {
        device.setOrientationLeft()
        displayListAndMapFromMap()
    }

    @Test
    fun displayListAndMapFromMapWithRotationRight() {
        device.setOrientationRight()
        displayListAndMapFromMap()
    }
}