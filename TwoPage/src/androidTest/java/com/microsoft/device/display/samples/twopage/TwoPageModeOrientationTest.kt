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
        unfreezeRotation()
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
        switchFromSingleToDualScreen()

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
        setOrientationLeft()

        onView(withId(R.id.pager)).check(matches(isDisplayed()))
        onView(allOf(withId(R.id.app_name_text), withText("Two Page")))
            .check(matches(isDisplayed()))

        horizontalSwipeToLeft()

        onView(allOf(withId(R.id.page2_page_number), withText("PAGE 2 of 4")))
            .check(matches(isDisplayed()))
    }

    @Test
    fun layoutInDualScreenLandscape() {
        setOrientationLeft()
        switchFromSingleToDualScreen()

        onView(withId(R.id.pager)).check(matches(isDisplayed()))
        onView(allOf(withId(R.id.app_name_text), withText("Two Page")))
            .check(matches(isDisplayed()))

        verticalSwipeToTop()

        onView(allOf(withId(R.id.page2_page_number), withText("PAGE 2 of 4")))
            .check(matches(isDisplayed()))
    }
}
