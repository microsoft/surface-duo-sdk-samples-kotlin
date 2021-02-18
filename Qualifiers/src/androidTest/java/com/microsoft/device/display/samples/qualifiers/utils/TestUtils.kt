/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.qualifiers.utils

import androidx.annotation.ColorInt

/**
 * Returns a matcher that matches views that has the expected background color
 */
fun hasBackgroundColor(@ColorInt color: Int?) = BackgroundColorMatcher(color)