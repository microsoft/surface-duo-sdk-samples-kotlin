/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.listdetail.utils

import android.graphics.drawable.BitmapDrawable
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher

/**
 * A matcher that matches {@link ImageView}s based on its drawable background id.
 */
class DrawableMatcher(private val drawableId: Int) : TypeSafeMatcher<View?>(View::class.java) {
    private var resourceName: String? = null

    override fun matchesSafely(target: View?): Boolean {
        if (target == null) {
            return false
        }

        if (target !is ImageView) {
            return false
        }

        if (drawableId < 0) {
            return target.drawable == null
        }

        resourceName = target.getContext().resources.getResourceEntryName(drawableId)

        val expectedDrawable = ContextCompat.getDrawable(target.context, drawableId) ?: return false
        val targetBitmap = (target.drawable as BitmapDrawable).bitmap
        val expectedBitmap = (expectedDrawable as BitmapDrawable).bitmap
        return targetBitmap.sameAs(expectedBitmap)
    }

    override fun describeTo(description: Description) {
        description.appendText("with drawable from resource id: ")
        description.appendValue(drawableId)
        resourceName?.let {
            description.appendText("[")
            description.appendText(it)
            description.appendText("]")
        }
    }
}