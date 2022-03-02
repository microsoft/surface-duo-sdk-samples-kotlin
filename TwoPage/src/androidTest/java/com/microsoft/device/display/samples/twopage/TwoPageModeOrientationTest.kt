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
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import com.microsoft.device.display.samples.twopage.utils.ViewPagerIdlingResource
import com.microsoft.device.display.samples.twopage.utils.horizontalSwipeToLeft
import com.microsoft.device.display.samples.twopage.utils.verticalSwipeToTop
import com.microsoft.device.dualscreen.testing.resetOrientation
import com.microsoft.device.dualscreen.testing.spanFromStart
import org.hamcrest.core.AllOf.allOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
@LargeTest
class TwoPageModeOrientationTest {

    @get:Rule
    val activityScenarioRule = activityScenarioRule<TwoPageActivity>()
    private lateinit var idlingResource: ViewPagerIdlingResource
    private val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

    @Before
    fun setup() {
        activityScenarioRule.scenario.onActivity { activity ->
            idlingResource = ViewPagerIdlingResource(activity.findViewById(R.id.pager))
        }

        IdlingRegistry.getInstance().register(idlingResource)
    }

    @After
    fun resetOrientation() {
        IdlingRegistry.getInstance().unregister(idlingResource)
        device.resetOrientation()
    }

    @Test
    fun layoutInSingleScreenPortrait() {
        onView(withId(R.id.pager)).check(matches(isDisplayed()))
        onView(allOf(withId(R.id.app_name_text), withText("Two Page")))
            .check(matches(isDisplayed()))

        onView(withId(R.id.pager)).perform(swipeLeft())

        onView(allOf(withId(R.id.page2_page_number), withText("PAGE 2 of 4")))
            .check(matches(isDisplayed()))
    }

    @Test
    fun layoutInDualScreenPortrait() {
        device.spanFromStart()

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
    fun layoutInSingleScreenLandscape() {
        device.setOrientationLeft()

        onView(withId(R.id.pager)).check(matches(isDisplayed()))
        onView(allOf(withId(R.id.app_name_text), withText("Two Page")))
            .check(matches(isDisplayed()))

        horizontalSwipeToLeft()

        onView(allOf(withId(R.id.page2_page_number), withText("PAGE 2 of 4")))
            .check(matches(isDisplayed()))
    }

    @Test
    fun layoutInDualScreenLandscape() {
        device.setOrientationLeft()
        device.spanFromStart()

        onView(withId(R.id.pager)).check(matches(isDisplayed()))
        onView(allOf(withId(R.id.app_name_text), withText("Two Page")))
            .check(matches(isDisplayed()))

        verticalSwipeToTop()

        onView(allOf(withId(R.id.page2_page_number), withText("PAGE 2 of 4")))
            .check(matches(isDisplayed()))
    }
}
