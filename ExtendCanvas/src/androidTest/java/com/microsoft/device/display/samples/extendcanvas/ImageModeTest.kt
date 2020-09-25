/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.extendcanvas

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import androidx.test.uiautomator.UiDevice
import com.microsoft.device.display.samples.extend.R
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
@LargeTest
class ImageModeTest {

    private val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

    @get:Rule
    val activityRule = ActivityTestRule<MainActivity>(MainActivity::class.java)

    @Test
    fun displaySmallMapImage_whenIsSingleScreen() {
        onView(withId(R.id.img_view)).check(
            matches(
                allOf(
                    isDisplayed(),
                    withDesiredWidth(SCREEN_WIDTH, R.id.img_view)
                )
            )
        )
    }

    @Test
    fun displayLargeMapImage_whenIsDualScreen() {
        spanApplication()

        onView(withId(R.id.img_view)).check(
            matches(
                allOf(
                    isDisplayed(),
                    withDesiredWidth(
                        SCREEN_WIDTH * SCREENS_COUNT_DUAL_MODE + HINGE_WIDTH,
                        R.id.img_view
                    )
                )
            )
        )
    }

    private fun spanApplication() {
        device.swipe(675, 1780, 1350, 900, 400)
    }

    companion object {
        const val SCREENS_COUNT_DUAL_MODE = 2
        const val SCREEN_WIDTH = 1350
        const val HINGE_WIDTH = 84

        fun withDesiredWidth(desiredWidth: Int, itemViewId: Int): Matcher<View> =
            object : TypeSafeMatcher<View>() {
                override fun describeTo(description: Description?) {
                    description?.appendText(
                        "Check for item with id $itemViewId to have the width equal to $desiredWidth"
                    )
                }

                override fun matchesSafely(view: View?): Boolean {
                    view?.let {
                        return desiredWidth == it.findViewById<View>(itemViewId).width
                    }
                    return false
                }
            }
    }
}
