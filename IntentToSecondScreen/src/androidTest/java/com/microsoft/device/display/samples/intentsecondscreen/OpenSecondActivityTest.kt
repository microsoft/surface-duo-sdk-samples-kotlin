/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.intentsecondscreen

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.matcher.IntentMatchers.hasFlags
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import com.microsoft.device.display.samples.test.utils.forceClick
import com.microsoft.device.dualscreen.testing.resetOrientation
import com.microsoft.device.dualscreen.testing.spanFromStart
import org.hamcrest.core.AllOf.allOf
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
@LargeTest
class OpenSecondActivityTest {
    @get:Rule
    val intentsTestRule = IntentsTestRule(IntentToSecondScreenFirstActivity::class.java)
    private val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

    @After
    fun tearDown() {
        device.resetOrientation()
    }

    @Test
    fun singleScreenModeInPortrait() {
        openSecondScreen()
        checkIntentForTheSecondScreen()
        checkTheSecondScreen()
    }

    @Test
    fun dualScreenModeInPortrait() {
        device.spanFromStart()
        openSecondScreen()
        checkIntentForTheSecondScreen()
        checkTheSecondScreen()
    }

    @Test
    fun singleScreenModeInLandscape() {
        device.setOrientationLeft()
        singleScreenModeInPortrait()
    }

    @Test
    fun dualScreenModeInLandscape() {
        device.setOrientationLeft()
        dualScreenModeInPortrait()
    }

    private fun openSecondScreen() {
        onView(withId(R.id.open_second_screen)).perform(forceClick())
    }

    private fun checkIntentForTheSecondScreen() {
        intended(
            allOf(
                hasComponent(IntentToSecondScreenSecondActivity::class.java.name),
                hasFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            )
        )
    }

    private fun checkTheSecondScreen() {
        onView(withId(R.id.text_second_screen)).check(matches(isDisplayed()))
    }
}