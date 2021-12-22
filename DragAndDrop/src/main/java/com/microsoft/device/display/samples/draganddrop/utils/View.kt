/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.draganddrop.utils

import android.app.Activity
import android.content.ClipData
import android.content.ContentResolver
import android.graphics.ColorFilter
import android.view.DragEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityCompat

/**
 * Returns the corresponding mime type for each kind of view
 */
val View.mimeType: MimeType?
    get() = when (this) {
        is ImageView -> MimeType.IMAGE_JPEG
        is TextView -> MimeType.TEXT_PLAIN
        else -> null
    }

/**
 * Returns the view parent as a [ViewGroup] if the cast is possible, otherwise will return null
 */
val View.parentViewGroup: ViewGroup?
    get() = parent as? ViewGroup

/**
 * Removes a list of [View]'s from their parents
 */
fun removeViews(vararg views: View?) {
    views.forEach { it?.remove() }
}

/**
 * Removes the [View] from his parent
 */
fun View.remove() {
    parentViewGroup?.removeView(this)
}

/**
 * Replaces a view with another view keeping the same [ViewGroup.LayoutParams]
 *
 * @param view The new [View] that will be added
 */
fun View.replaceWith(view: View?) {
    val parent: ViewGroup = parentViewGroup ?: return
    val layoutParams = layoutParams
    removeViews(this, view)
    parent.addView(view, layoutParams)
}

/**
 * Updates the color and elevation for the given view
 *
 * @param colorFilter The new color
 * @param elevation The new elevation
 */
fun View.updateColorAndElevation(colorFilter: ColorFilter?, elevation: Float) {
    background.colorFilter = colorFilter
    this.elevation = elevation
    invalidate()
}

/**
 * Sets the elevation for the given list of [View]'s
 */
fun setElevation(elevation: Float, vararg views: View?) {
    views.filterNotNull()
        .forEach { it.elevation = elevation }
}

/**
 * Sets the image URI in drag and drop context.
 *
 * @param activity The activity container
 * @param event The drag and drop event
 * @param clipDataItem The clip data item containing the image URI
 */
fun ImageView.setDragAndDropImageURI(
    activity: Activity,
    event: DragEvent,
    clipDataItem: ClipData.Item
) {
    val uri = clipDataItem.uri
    if (ContentResolver.SCHEME_CONTENT == uri.scheme) {
        // Accessing a "content" scheme Uri requires a permission grant.
        ActivityCompat.requestDragAndDropPermissions(activity, event)
            ?: return // Permission could not be obtained.return
        setImageURI(uri)
    } else {
        // Other schemes (such as "android.resource") do not require a permission grant.
        setImageURI(uri)
    }
}
