/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.contentcontext

import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import androidx.test.uiautomator.UiDevice
import org.hamcrest.`object`.HasToString.hasToString
import org.hamcrest.core.AllOf.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
@LargeTest
class LayoutModeTest {

    private val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

    @get:Rule
    val activityRule = ActivityTestRule<MainActivity>(MainActivity::class.java)

    @Test
    fun openMapFromList_whenIsSingleScreen() {
        onView(withId(R.id.single_screen_container_id)).check(matches(isDisplayed()))
        onView(withId(R.id.lvItems)).check(matches(isDisplayed()))

        onData(hasToString("New York"))
            .inAdapterView(withId(R.id.lvItems))
            .perform(click())
        onView(withId(R.id.img_view)).check(matches(isDisplayed()))
        onView(withId(R.id.detail_toolbar)).check(
            matches(
                allOf(
                    isDisplayed(),
                    hasDescendant(withText("New York"))
                )
            )
        )
    }

    @Test
    fun displayListAndDetailsFromList_whenIsDualScreen() {
        spanApplication()

        onView(withId(R.id.dual_screen_start_container_id)).check(matches(isDisplayed()))
        onView(withId(R.id.dual_screen_end_container_id)).check(matches(isDisplayed()))

        onView(withId(R.id.lvItems)).check(matches(isDisplayed()))
        onView(withId(R.id.img_view)).check(matches(isDisplayed()))
        onView(withId(R.id.detail_toolbar)).check(
            matches(
                allOf(
                    isDisplayed(),
                    hasDescendant(withText("New York"))
                )
            )
        )
    }

    @Test
    fun displayListAndDetailsFromMap_whenIsDualScreen() {
        onView(withId(R.id.single_screen_container_id)).check(matches(isDisplayed()))
        onView(withId(R.id.lvItems)).check(matches(isDisplayed()))

        onData(hasToString("New York"))
            .inAdapterView(withId(R.id.lvItems))
            .perform(click())

        spanApplication()

        onView(withId(R.id.dual_screen_start_container_id)).check(matches(isDisplayed()))
        onView(withId(R.id.dual_screen_end_container_id)).check(matches(isDisplayed()))

        onView(withId(R.id.lvItems)).check(matches(isDisplayed()))
        onView(withId(R.id.img_view)).check(matches(isDisplayed()))
        onView(withId(R.id.detail_toolbar)).check(
            matches(
                allOf(
                    isDisplayed(),
                    hasDescendant(withText("New York"))
                )
            )
        )
    }

    private fun spanApplication() {
        device.swipe(675, 1780, 1350, 900, 400)
    }
}
