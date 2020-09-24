/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.qualifiers

import android.graphics.drawable.ColorDrawable
import android.view.View
import androidx.annotation.ColorInt
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers.withContentDescription
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import androidx.test.uiautomator.UiDevice
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
@LargeTest
class QualifiersTest {

    private val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

    @get:Rule
    val activityRule = ActivityTestRule<MainActivity>(MainActivity::class.java)

    @Test
    fun displaySingleLayout_inSingleMode() {
        onView(withId(R.id.layout_container))
            .check(matches(withBackgroundColor(null)))
        onView(withId(R.id.image))
            .check(matches(withContentDescription("Single screen image")))
        onView(withId(R.id.text))
            .check(matches(withText("Hello World! \nSingle screen mode")))
        onView(withId(R.id.resource_folder_text))
            .check(matches(withText("sw540dp-1350x1800")))
    }

    @Test
    fun displayDualLayout_inDualMode() {
        spanApplication()

        onView(withId(R.id.layout_container))
            .check(matches(withBackgroundColor(R.color.colorAccent)))
        onView(withId(R.id.image))
            .check(matches(withContentDescription("Dual screen image")))
        onView(withId(R.id.text))
            .check(matches(withText("Hello World! \nSpanned mode!")))
        onView(withId(R.id.resource_folder_text))
            .check(matches(withText("sw720dp-2784x1800")))
    }

    private fun spanApplication() {
        device.swipe(675, 1780, 1350, 900, 400)
    }

    companion object {
        fun withBackgroundColor(@ColorInt expectedColor: Int?): Matcher<View> =
            object : BoundedMatcher<View, ConstraintLayout>(ConstraintLayout::class.java) {
                override fun describeTo(description: Description?) {
                    description?.appendText(
                        "Check for item to have color with id $expectedColor"
                    )
                }

                override fun matchesSafely(view: ConstraintLayout?): Boolean {
                    view?.let {
                        if (expectedColor == null) {
                            return view.background == null
                        }
                        if (view.background is ColorDrawable) {
                            val actualColor = (view.background as ColorDrawable).color
                            return actualColor == view.context.getColor(expectedColor)
                        }
                    }
                    return false
                }
            }
    }
}
