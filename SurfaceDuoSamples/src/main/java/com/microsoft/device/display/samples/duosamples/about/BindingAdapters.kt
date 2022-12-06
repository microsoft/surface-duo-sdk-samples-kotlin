/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.duosamples.about

import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter

@BindingAdapter("visibleIf")
fun visibleIf(view: View, shouldBeVisible: Boolean?) {
    view.isVisible = shouldBeVisible == true
}

/**
 * Replaces accessibility services' click action label to something more specific
 * E.g. TalkBack's default announcement for clickable items is "Double tap to activate" and this
 * replaces the "activate" label.
 */
@BindingAdapter("clickActionLabel")
fun replaceClickActionLabel(view: View, label: String?) {
    ViewCompat.replaceAccessibilityAction(
        view,
        AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_CLICK,
        label,
        null
    )
}