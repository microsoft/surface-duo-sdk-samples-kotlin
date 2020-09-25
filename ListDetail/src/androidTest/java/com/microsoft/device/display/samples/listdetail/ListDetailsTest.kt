/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.listdetail

import android.view.View
import android.widget.ListView
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import androidx.test.uiautomator.UiDevice
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.hasToString
import org.hamcrest.core.AllOf.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
@LargeTest
class ListDetailsTest {
    private val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

    @get:Rule
    val activityRule = ActivityTestRule<MainActivity>(MainActivity::class.java)

    @Test
    fun openDetailsFromList_whenInSingleMode() {
        onView(withId(R.id.single_screen_container_id)).check(matches(isDisplayed()))
        onView(withId(R.id.list_view)).check(matches(isDisplayed()))

        onData(hasToString("Item 2"))
            .inAdapterView(withId(R.id.list_view))
            .perform(click())

        onView(allOf(withId(R.id.tvTitle), withText("Item 2"))).check(matches(isDisplayed()))
    }

    @Test
    fun displayListAndDetails_whenInDualMode() {
        spanApplication()

        onView(withId(R.id.dual_screen_start_container_id)).check(matches(isDisplayed()))
        onView(withId(R.id.dual_screen_end_container_id)).check(matches(isDisplayed()))

        onView(withId(R.id.list_view)).check(matches(isDisplayed()))
        onView(withId(R.id.tvTitle)).check(matches(isDisplayed()))
    }

    @Test
    fun checkLinkBetweenListAndDetail_whenInDualMode() {
        spanApplication()

        onView(withId(R.id.list_view)).check(matches(isDisplayed()))

        onData(hasToString("Item 2"))
            .inAdapterView(withId(R.id.list_view))
            .perform(click())

        onView(withId(R.id.list_view)).check(matches(isItemChecked(1)))
        onView(allOf(withId(R.id.tvTitle), withText("Item 2"))).check(matches(isDisplayed()))
    }

    private fun spanApplication() {
        device.swipe(675, 1780, 1350, 900, 400)
    }

    companion object {
        fun isItemChecked(itemPosition: Int): Matcher<View> =
            object : BoundedMatcher<View, ListView>(ListView::class.java) {
                override fun describeTo(description: Description?) {
                    description?.appendText(
                        "Check if item from position $itemPosition is checked"
                    )
                }

                override fun matchesSafely(listView: ListView?): Boolean {
                    return listView?.isItemChecked(itemPosition) == true
                }
            }
    }
}
