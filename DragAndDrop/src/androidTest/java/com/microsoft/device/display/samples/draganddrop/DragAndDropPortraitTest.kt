/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.draganddrop

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withTagValue
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.microsoft.device.display.samples.draganddrop.utils.childOf
import com.microsoft.device.display.samples.draganddrop.utils.dragAndDropImageInDualScreenMode
import com.microsoft.device.display.samples.draganddrop.utils.dragAndDropImageInSingleScreenMode
import com.microsoft.device.display.samples.draganddrop.utils.dragAndDropTextInDualScreenMode
import com.microsoft.device.display.samples.draganddrop.utils.dragAndDropTextInSingleScreenMode
import com.microsoft.device.display.samples.draganddrop.utils.switchFromSingleToDualScreen
import org.hamcrest.CoreMatchers.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.hamcrest.CoreMatchers.`is` as iz

@RunWith(AndroidJUnit4ClassRunner::class)
@LargeTest
open class DragAndDropPortraitTest {
    @get:Rule
    val activityScenarioRule = activityScenarioRule<DragAndDropActivity>()

    @Test
    fun dragAndDropImageInSingleMode() {
        dragAndDropImageInSingleScreenMode()

        onView(
            childOf(
                withId(R.id.drop_target_container),
                withId(R.id.drag_image_view)
            )
        ).check(
            matches(
                allOf(
                    isDisplayed(),
                    withTagValue(iz("image_view" as Any))
                )
            )
        )
    }

    @Test
    fun dragAndDropTextInSingleMode() {
        dragAndDropTextInSingleScreenMode()

        onView(
            childOf(
                withId(R.id.drop_target_container),
                withId(R.id.drag_text_view)
            )
        ).check(
            matches(
                allOf(
                    isDisplayed(),
                    withText(R.string.plain_text),
                    withTagValue(iz("text_view" as Any))
                )
            )
        )
    }

    @Test
    fun dragAndDropImageInDualMode() {
        switchFromSingleToDualScreen()

        onView(
            childOf(
                withId(R.id.first_container_id),
                withId(R.id.drag_image_view)
            )
        ).check(matches(isDisplayed()))

        dragAndDropImageInDualScreenMode()

        onView(
            childOf(
                withId(R.id.second_container_id),
                withId(R.id.drag_image_view)
            )
        ).check(
            matches(
                allOf(
                    isDisplayed(),
                    withTagValue(iz("image_view" as Any))
                )
            )
        )
    }

    @Test
    fun dragAndDropTextInDualMode() {
        switchFromSingleToDualScreen()

        onView(
            childOf(
                withId(R.id.first_container_id),
                withId(R.id.drag_text_view)
            )
        ).check(matches(isDisplayed()))

        dragAndDropTextInDualScreenMode()

        onView(
            childOf(
                withId(R.id.second_container_id),
                withId(R.id.drag_text_view)
            )
        ).check(
            matches(
                allOf(
                    isDisplayed(),
                    withText(R.string.plain_text),
                    withTagValue(iz("text_view" as Any))
                )
            )
        )
    }
}
