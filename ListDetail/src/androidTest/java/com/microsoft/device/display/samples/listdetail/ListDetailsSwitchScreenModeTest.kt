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
import com.microsoft.device.display.samples.listdetail.utils.hasDrawable
import com.microsoft.device.display.samples.test.utils.forceClick
import com.microsoft.device.display.samples.test.utils.setOrientationLeft
import com.microsoft.device.display.samples.test.utils.setOrientationRight
import com.microsoft.device.display.samples.test.utils.switchFromDualToSingleScreen
import com.microsoft.device.display.samples.test.utils.switchFromSingleToDualScreen
import com.microsoft.device.display.samples.test.utils.unfreezeRotation
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
@LargeTest
class ListDetailsSwitchScreenModeTest {
    @get:Rule
    val activityScenarioRule = activityScenarioRule<ListDetailsActivity>()

    @After
    fun tearDown() {
        unfreezeRotation()
    }

    @Test
    fun testSwitchScreenMode() {
        checkInSingleScreenMode()
        switchFromSingleToDualScreen()
        checkInDualScreenMode()
        switchFromDualToSingleScreen()
        checkInSingleScreenMode()
    }

    @Test
    fun testSwitchScreenModeWithOrientationLeft() {
        setOrientationLeft()
        checkInSingleScreenMode()
        switchFromSingleToDualScreen()
        checkInSingleScreenMode()
        switchFromDualToSingleScreen()
        checkInSingleScreenMode()
    }

    @Test
    fun testSwitchScreenModeWithOrientationRight() {
        setOrientationRight()
        checkInSingleScreenMode()
        switchFromSingleToDualScreen()
        checkInSingleScreenMode()
        switchFromDualToSingleScreen()
        checkInSingleScreenMode()
    }

    private fun checkInSingleScreenMode() {
        onView(withId(R.id.first_container_id)).check(matches(isDisplayed()))
        onView(withId(R.id.imagesRecyclerView)).check(matches(isDisplayed()))

        onView(withId(R.id.imagesRecyclerView)).perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(1, forceClick()))
        onView(withId(R.id.imageView)).check(matches(isDisplayed())).check(matches(hasDrawable(R.drawable.list_details_image_2)))
    }

    private fun checkInDualScreenMode() {
        onView(withId(R.id.first_container_id)).check(matches(isDisplayed()))
        onView(withId(R.id.second_container_id)).check(matches(isDisplayed()))
        onView(withId(R.id.imagesRecyclerView)).check(matches(isDisplayed()))

        onView(withId(R.id.imagesRecyclerView)).perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(2, forceClick()))
        onView(withId(R.id.imageView)).check(matches(isDisplayed())).check(matches(hasDrawable(R.drawable.list_details_image_3)))
    }
}