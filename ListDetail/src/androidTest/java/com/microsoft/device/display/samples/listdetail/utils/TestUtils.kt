/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.listdetail.utils

/**
 * Returns a matcher that matches {@link ImageViews Views} that has the expected drawable background.
 * @param drawableId The expected Drawable resource id
 */
fun hasDrawable(drawableId: Int) = DrawableMatcher(drawableId)