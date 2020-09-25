/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.draganddrop

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withTagValue
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import androidx.test.uiautomator.UiDevice
import org.hamcrest.CoreMatchers.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.hamcrest.CoreMatchers.`is` as iz

@RunWith(AndroidJUnit4ClassRunner::class)
@LargeTest
class DragAndDropTest {

    private val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

    @get:Rule
    val activityRule = ActivityTestRule<MainActivity>(MainActivity::class.java)

    @Test
    fun shouldContainImage_whenDragAndDropInSingleMode() {
        device.swipe(300, 600, 300, 1300, 400)

        onView(withId(R.id.drop_image_container)).check(
            matches(
                allOf(
                    isDisplayed(),
                    hasDescendant(withTagValue(iz("image_view" as Any)))
                )
            )
        )
    }

    @Test
    fun shouldContainText_whenDragAndDropInSingleMode() {
        device.swipe(1000, 600, 1000, 1300, 400)

        onView(withId(R.id.drop_text_container)).check(
            matches(
                allOf(
                    isDisplayed(),
                    hasDescendant(withText(R.string.plain_text)),
                    hasDescendant(withTagValue(iz("text_view" as Any)))
                )
            )
        )
    }

    @Test
    fun shouldContainImage_whenDragAndDropInDualMode() {
        spanApplication()

        onView(withId(R.id.drag_image_view)).check(matches(isDisplayed()))

        device.swipe(300, 1000, 1800, 1000, 400)

        onView(withId(R.id.drop_image_container)).check(
            matches(
                allOf(
                    isDisplayed(),
                    hasDescendant(withTagValue(iz("image_view" as Any)))
                )
            )
        )
    }

    @Test
    fun shouldContainText_whenDragAndDropInDualMode() {
        spanApplication()

        onView(withId(R.id.drag_text_view)).check(matches(isDisplayed()))

        device.swipe(900, 900, 2500, 900, 400)

        onView(withId(R.id.drop_text_container)).check(
            matches(
                allOf(
                    isDisplayed(),
                    hasDescendant(withText(R.string.plain_text)),
                    hasDescendant(withTagValue(iz("text_view" as Any)))
                )
            )
        )
    }

    private fun spanApplication() {
        device.swipe(675, 1780, 1350, 900, 400)
    }
}
