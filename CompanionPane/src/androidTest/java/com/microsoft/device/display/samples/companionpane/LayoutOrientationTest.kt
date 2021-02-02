/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.companionpane

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import androidx.test.uiautomator.UiDevice
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
@LargeTest
class LayoutOrientationTest {

    private val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
    private val device2 = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation()) //This is a test

    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java)

    @After
    fun resetOrientation() {
        device.setOrientationNatural()
        device.unfreezeRotation()
    }

    @Test
    fun shouldFindLayout_whenInSinglePortrait() {
        onView(withId(R.id.first_container_id)).check(matches(isDisplayed()))
        onView(withId(R.id.single_screen_layout_port)).check(matches(isDisplayed()))
    }

    @Test
    fun shouldFindLayout_whenInSingleLandscape() {
        rotateDevice()

        onView(withId(R.id.first_container_id)).check(matches(isDisplayed()))
        onView(withId(R.id.single_screen_layout_land)).check(matches(isDisplayed()))
    }

    @Test
    fun shouldFindLayouts_whenInDoublePortrait() {
        spanApplication()

        onView(withId(R.id.first_container_id)).check(matches(isDisplayed()))
        onView(withId(R.id.dual_screen_layout_land_start)).check(matches(isDisplayed()))

        onView(withId(R.id.second_container_id)).check(matches(isDisplayed()))
        onView(withId(R.id.dual_screen_layout_land_end)).check(matches(isDisplayed()))
    }

    @Test
    fun shouldFindLayouts_whenInDoubleLandscape() {
        spanApplication()
        rotateDevice()

        onView(withId(R.id.first_container_id)).check(matches(isDisplayed()))
        onView(withId(R.id.dual_screen_layout_port_start)).check(matches(isDisplayed()))

        onView(withId(R.id.second_container_id)).check(matches(isDisplayed()))
        onView(withId(R.id.dual_screen_layout_port_end)).check(matches(isDisplayed()))
    }

    private fun spanApplication() {
        device.swipe(675, 1780, 1350, 900, 400)
    }

    private fun rotateDevice() {
        device.setOrientationLeft()
    }
}