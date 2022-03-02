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
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import com.microsoft.device.dualscreen.testing.resetOrientation
import com.microsoft.device.dualscreen.testing.spanFromStart
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
@LargeTest
class CompanionPaneLayoutTest {

    @get:Rule
    val activityScenarioRule = activityScenarioRule<CompanionPaneActivity>()
    private val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

    @After
    fun tearDown() {
        device.resetOrientation()
    }

    @Test
    fun singleScreenModeInPortrait() {
        onView(withId(R.id.first_container_id)).check(matches(isDisplayed()))
        onView(withId(R.id.single_screen_layout_port)).check(matches(isDisplayed()))
    }

    @Test
    fun singleScreenModeInLandscape() {
        device.setOrientationLeft()

        onView(withId(R.id.first_container_id)).check(matches(isDisplayed()))
        onView(withId(R.id.single_screen_layout_land)).check(matches(isDisplayed()))
    }

    @Test
    fun dualScreenModeInPortrait() {
        device.spanFromStart()

        onView(withId(R.id.first_container_id)).check(matches(isDisplayed()))
        onView(withId(R.id.dual_screen_layout_land_start)).check(matches(isDisplayed()))

        onView(withId(R.id.second_container_id)).check(matches(isDisplayed()))
        onView(withId(R.id.dual_screen_layout_land_end)).check(matches(isDisplayed()))
    }

    @Test
    fun dualScreenModeInLandscape() {
        device.spanFromStart()
        device.setOrientationLeft()

        onView(withId(R.id.first_container_id)).check(matches(isDisplayed()))
        onView(withId(R.id.dual_screen_layout_port_start)).check(matches(isDisplayed()))

        onView(withId(R.id.second_container_id)).check(matches(isDisplayed()))
        onView(withId(R.id.dual_screen_layout_port_end)).check(matches(isDisplayed()))
    }
}