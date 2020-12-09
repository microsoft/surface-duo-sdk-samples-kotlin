/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.contentcontext

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import androidx.test.uiautomator.UiDevice
import com.microsoft.device.display.samples.contentcontext.view.RestaurantAdapter
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
@LargeTest
class LayoutModeTest {

    private val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun openMapFromList_whenIsSingleScreen() {
        onView(withId(R.id.first_container_id)).check(matches(isDisplayed()))
        onView(withId(R.id.list_items)).check(matches(isDisplayed()))

        onView(withId(R.id.list_items)).perform(
            actionOnItemAtPosition<RestaurantAdapter.RestaurantViewHolder>(3, click())
        )
        onView(withId(R.id.img_view)).check(matches(isDisplayed()))
    }

    @Test
    fun displayListAndDetailsFromList_whenIsDualScreen() {
        spanApplication()

        onView(withId(R.id.first_container_id)).check(matches(isDisplayed()))
        onView(withId(R.id.second_container_id)).check(matches(isDisplayed()))

        onView(withId(R.id.list_items)).check(matches(isDisplayed()))
        onView(withId(R.id.img_view)).check(matches(isDisplayed()))
    }

    @Test
    fun displayListAndDetailsFromMap_whenIsDualScreen() {
        onView(withId(R.id.first_container_id)).check(matches(isDisplayed()))
        onView(withId(R.id.list_items)).check(matches(isDisplayed()))

        onView(withId(R.id.list_items)).perform(
            actionOnItemAtPosition<RestaurantAdapter.RestaurantViewHolder>(3, click())
        )

        spanApplication()

        onView(withId(R.id.first_container_id)).check(matches(isDisplayed()))
        onView(withId(R.id.second_container_id)).check(matches(isDisplayed()))

        onView(withId(R.id.list_items)).check(matches(isDisplayed()))
        onView(withId(R.id.img_view)).check(matches(isDisplayed()))
    }

    private fun spanApplication() {
        device.swipe(675, 1780, 1350, 900, 400)
    }
}
