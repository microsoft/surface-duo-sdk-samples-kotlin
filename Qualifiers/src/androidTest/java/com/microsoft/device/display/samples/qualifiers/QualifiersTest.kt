/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.qualifiers

import androidx.annotation.ColorInt
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withContentDescription
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.microsoft.device.display.samples.qualifiers.test.R
import com.microsoft.device.display.samples.qualifiers.utils.DUAL_SCREEN_IMAGE_DESCRIPTION
import com.microsoft.device.display.samples.qualifiers.utils.DUAL_SCREEN_MESSAGE
import com.microsoft.device.display.samples.qualifiers.utils.DUAL_SCREEN_RESOURCE_FOLDER
import com.microsoft.device.display.samples.qualifiers.utils.SINGLE_SCREEN_IMAGE_DESCRIPTION
import com.microsoft.device.display.samples.qualifiers.utils.SINGLE_SCREEN_MESSAGE
import com.microsoft.device.display.samples.qualifiers.utils.SINGLE_SCREEN_RESOURCE_FOLDER
import com.microsoft.device.display.samples.qualifiers.utils.hasBackgroundColor
import com.microsoft.device.display.samples.test.utils.setOrientationLeft
import com.microsoft.device.display.samples.test.utils.switchFromSingleToDualScreen
import com.microsoft.device.display.samples.test.utils.unfreezeRotation
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
@LargeTest
class QualifiersTest {
    @get:Rule
    val activityScenarioRule = activityScenarioRule<QualifiersActivity>()

    @After
    fun tearDown() {
        unfreezeRotation()
    }

    @Test
    fun singleScreenModeInPortrait() {
        checkContent(
            null,
            SINGLE_SCREEN_IMAGE_DESCRIPTION,
            SINGLE_SCREEN_MESSAGE,
            SINGLE_SCREEN_RESOURCE_FOLDER
        )
    }

    @Test
    fun dualScreenModeInPortrait() {
        switchFromSingleToDualScreen()
        checkContent(
            R.color.colorAccent,
            DUAL_SCREEN_IMAGE_DESCRIPTION,
            DUAL_SCREEN_MESSAGE,
            DUAL_SCREEN_RESOURCE_FOLDER
        )
    }

    @Test
    fun singleScreenModeInLandscape() {
        setOrientationLeft()
        checkContent(
            null,
            SINGLE_SCREEN_IMAGE_DESCRIPTION,
            SINGLE_SCREEN_MESSAGE,
            SINGLE_SCREEN_RESOURCE_FOLDER
        )
    }

    @Test
    fun dualScreenModeInLandscape() {
        switchFromSingleToDualScreen()
        setOrientationLeft()
        checkContent(
            R.color.colorAccent,
            DUAL_SCREEN_IMAGE_DESCRIPTION,
            DUAL_SCREEN_MESSAGE,
            DUAL_SCREEN_RESOURCE_FOLDER
        )
    }

    private fun checkContent(
        @ColorInt expectedBackgroundColor: Int?,
        expectedImageContentDescription: String,
        expectedMessage: String,
        expectedResourceFolder: String
    ) {
        onView(withId(R.id.layout_container)).check(matches(hasBackgroundColor(expectedBackgroundColor)))
        onView(withId(R.id.image)).check(matches(withContentDescription(expectedImageContentDescription)))
        onView(withId(R.id.message)).check(matches(withText(expectedMessage)))
        onView(withId(R.id.resource_folder)).check(matches(withText(expectedResourceFolder)))
    }
}
