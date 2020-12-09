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
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import com.microsoft.device.display.samples.listdetail.utils.forceClick
import com.microsoft.device.display.samples.listdetail.utils.hasDrawable
import com.microsoft.device.display.samples.listdetail.utils.setOrientationLeft
import com.microsoft.device.display.samples.listdetail.utils.setOrientationRight
import com.microsoft.device.display.samples.listdetail.utils.switchFromSingleToDualScreen
import com.microsoft.device.display.samples.listdetail.utils.unfreezeRotation
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
@LargeTest
class ListDetailsInDualScreenModeTest {
    @get:Rule
    val activityRule = ActivityTestRule<MainActivity>(MainActivity::class.java)

    @After
    fun tearDown() {
        unfreezeRotation()
    }

    @Test
    fun displayListAndDetails() {
        switchFromSingleToDualScreen()

        onView(withId(R.id.first_container_id)).check(matches(isDisplayed()))
        onView(withId(R.id.image_list)).check(matches(isDisplayed()))

        onView(withId(R.id.image_list)).perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(2, forceClick()))
        onView(withId(R.id.imageView)).check(matches(isDisplayed())).check(matches(hasDrawable(R.drawable.image_3)))
    }

    @Test
    fun displayListAndDetailsWithRotationLeft() {
        setOrientationLeft()
        displayListAndDetails()
    }

    @Test
    fun displayListAndDetailsWithRotationRight() {
        setOrientationRight()
        displayListAndDetails()
    }
}