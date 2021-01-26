/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.qualifiers.utils

import android.graphics.drawable.ColorDrawable
import android.view.View
import androidx.annotation.ColorInt
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher

/**
 * A matcher that matches View's based on his background color.
 */
class BackgroundColorMatcher(@ColorInt private val color: Int?) : TypeSafeMatcher<View?>(View::class.java) {
    private var resourceName: String? = null

    override fun matchesSafely(view: View?): Boolean {
        if (color != null && view != null) {
            resourceName = view.context.resources.getResourceEntryName(color)
        }

        return when {
            color == null -> view?.background == null

            view?.background is ColorDrawable -> {
                val actualColor = (view.background as ColorDrawable).color
                return actualColor == view.context.getColor(color)
            }

            else -> false
        }
    }

    override fun describeTo(description: Description) {
        description.appendText("with background from resource color id: ")
        description.appendValue(color)
        resourceName?.let {
            description.appendText("[")
            description.appendText(it)
            description.appendText("]")
        }
    }
}