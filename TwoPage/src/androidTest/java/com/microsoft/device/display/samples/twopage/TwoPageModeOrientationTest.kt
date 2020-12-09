/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.twopage

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.swipeLeft
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import androidx.test.uiautomator.UiDevice
import org.hamcrest.core.AllOf.allOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
@LargeTest
class TwoPageModeOrientationTest {
    private val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

    @get:Rule
    val activityRule = ActivityTestRule<MainActivity>(MainActivity::class.java)
    private lateinit var idlingResource: ViewPagerIdlingResource

    @Before
    fun setup() {
        idlingResource = ViewPagerIdlingResource(activityRule.activity.findViewById(R.id.pager))
        IdlingRegistry.getInstance().register(idlingResource)
    }

    @After
    fun resetOrientation() {
        IdlingRegistry.getInstance().unregister(idlingResource)
        device.setOrientationNatural()
        device.unfreezeRotation()
    }

    @Test
    fun displayLayouts_whenInSingleScreenPortrait() {
        onView(withId(R.id.pager)).check(matches(isDisplayed()))
        onView(allOf(withId(R.id.app_name_text), withText("Two Page")))
            .check(matches(isDisplayed()))

        onView(withId(R.id.pager)).perform(swipeLeft())

        onView(allOf(withId(R.id.page2_page_number), withText("PAGE 2 of 4")))
            .check(matches(isDisplayed()))
    }

    @Test
    fun displayLayouts_whenInDualScreenPortrait() {
        spanApplication()

        onView(withId(R.id.pager)).check(matches(isDisplayed()))
        onView(allOf(withId(R.id.app_name_text), withText("Two Page")))
            .check(matches(isDisplayed()))
        onView(allOf(withId(R.id.page2_page_number), withText("PAGE 2 of 4")))
            .check(matches(isDisplayed()))

        onView(withId(R.id.pager)).perform(swipeLeft())

        onView(allOf(withId(R.id.page2_page_number), withText("PAGE 2 of 4")))
            .check(matches(isDisplayed()))
        onView(allOf(withId(R.id.page3_page_number), withText("PAGE 3 of 4")))
            .check(matches(isDisplayed()))

        onView(withId(R.id.pager)).perform(swipeLeft())

        onView(allOf(withId(R.id.page3_page_number), withText("PAGE 3 of 4")))
            .check(matches(isDisplayed()))
        onView(allOf(withId(R.id.page4_page_number), withText("PAGE 4 of 4")))
            .check(matches(isDisplayed()))
    }

    @Test
    fun displayLayouts_whenInSingleScreenLandscape() {
        rotateDevice()

        onView(withId(R.id.pager)).check(matches(isDisplayed()))
        onView(allOf(withId(R.id.app_name_text), withText("Two Page")))
            .check(matches(isDisplayed()))

        changePageLandscape()

        onView(allOf(withId(R.id.page2_page_number), withText("PAGE 2 of 4")))
            .check(matches(isDisplayed()))
    }

    @Test
    fun displayLayouts_whenInDualScreenLandscape() {
        rotateDevice()
        spanLandscapeApplication()

        onView(withId(R.id.pager)).check(matches(isDisplayed()))
        onView(allOf(withId(R.id.app_name_text), withText("Two Page")))
            .check(matches(isDisplayed()))

        changePageVertical()

        onView(allOf(withId(R.id.page2_page_number), withText("PAGE 2 of 4")))
            .check(matches(isDisplayed()))
    }

    private fun spanApplication() {
        device.swipe(675, 1780, 1350, 900, 400)
    }

    private fun spanLandscapeApplication() {
        device.swipe(1780, 2100, 1350, 1500, 400)
    }

    private fun rotateDevice() {
        device.setOrientationLeft()
    }

    private fun changePageLandscape() {
        device.swipe(1500, 2000, 200, 2000, 10)
    }

    private fun changePageVertical() {
        device.swipe(1000, 1000, 1000, 200, 10)
    }
}
