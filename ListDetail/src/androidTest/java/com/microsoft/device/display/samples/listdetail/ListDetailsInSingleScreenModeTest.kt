/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.listdetail

import androidx.recyclerview.widget.RecyclerView
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
import com.microsoft.device.display.samples.listdetail.utils.hasDrawable
import com.microsoft.device.display.samples.test.utils.forceClick
import com.microsoft.device.dualscreen.testing.resetOrientation
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
@LargeTest
class ListDetailsInSingleScreenModeTest {
    @get:Rule
    val activityScenarioRule = activityScenarioRule<ListDetailsActivity>()
    private val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

    @After
    fun tearDown() {
        device.resetOrientation()
    }

    @Test
    fun openDetailsFromList() {
        onView(withId(R.id.first_container_id)).check(matches(isDisplayed()))
        onView(withId(R.id.imagesRecyclerView)).check(matches(isDisplayed()))

        onView(withId(R.id.imagesRecyclerView)).perform(
            actionOnItemAtPosition<RecyclerView.ViewHolder>(
                1,
                forceClick()
            )
        )
        onView(withId(R.id.imageView)).check(matches(isDisplayed()))
            .check(matches(hasDrawable(R.drawable.list_details_image_2)))
    }

    @Test
    fun openDetailsFromListWithRotationLeft() {
        device.setOrientationLeft()
        openDetailsFromList()
    }

    @Test
    fun openDetailsFromListWithRotationRight() {
        device.setOrientationLeft()
        openDetailsFromList()
    }
}
