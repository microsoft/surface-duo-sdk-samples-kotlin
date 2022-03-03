/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.test.utils

import android.view.View
import androidx.test.espresso.ViewAction
import com.microsoft.device.dualscreen.testing.ForceClick
import org.hamcrest.Matcher

/**
 * Returns A matcher that matches [View]s child's in parent matcher
 *
 * @return the matcher
 */
fun childOf(parentMatcher: Matcher<View>, childMatcher: Matcher<View>): Matcher<View> =
    ChildOfMatcher(parentMatcher, childMatcher)

/**
 * Returns an action that clicks the view without to check the coordinates on the screen.
 * Seems that ViewActions.click() finds coordinates of the view on the screen, and then performs the tap on the coordinates.
 * Seems that changing the screen rotations affects these coordinates and ViewActions.click() throws exceptions.
 */
fun forceClick(): ViewAction = ForceClick()
