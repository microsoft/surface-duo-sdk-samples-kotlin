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
import com.microsoft.device.display.samples.qualifiers.utils.hasBackgroundColor
import com.microsoft.device.display.samples.qualifiers.utils.setOrientationLeft
import com.microsoft.device.display.samples.qualifiers.utils.switchFromSingleToDualScreen
import com.microsoft.device.display.samples.qualifiers.utils.unfreezeRotation
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
        checkContent(null)
    }

    @Test
    fun dualScreenModeInPortrait() {
        switchFromSingleToDualScreen()
        checkContent(R.color.colorAccent)
    }

    @Test
    fun singleScreenModeInLandscape() {
        setOrientationLeft()
        checkContent(null)
    }

    @Test
    fun dualScreenModeInLandscape() {
        switchFromSingleToDualScreen()
        setOrientationLeft()
        checkContent(R.color.colorAccent)
    }

    private fun checkContent(@ColorInt backgroundColor: Int?) {
        onView(withId(R.id.layout_container)).check(matches(hasBackgroundColor(backgroundColor)))
        onView(withId(R.id.image)).check(matches(withContentDescription(R.string.sticker_image_content_description)))
        onView(withId(R.id.message)).check(matches(withText(R.string.hello_world)))
        onView(withId(R.id.resource_folder)).check(matches(withText(R.string.resource_folder)))
    }
}
