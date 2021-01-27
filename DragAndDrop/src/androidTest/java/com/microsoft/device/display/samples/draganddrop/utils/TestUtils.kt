/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.draganddrop.utils

import android.view.View
import org.hamcrest.Matcher

/**
 * Returns A matcher that matches [View]s child's in parent matcher
 *
 * @return the matcher
 */
fun childOf(parentMatcher: Matcher<View>, childMatcher: Matcher<View>): Matcher<View> =
    ChildOfMatcher(parentMatcher, childMatcher)
