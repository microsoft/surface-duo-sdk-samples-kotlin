/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.duosamples.about

import android.graphics.Color
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.widget.TextView

fun TextView.addClickableLink(textToLink: String, onClickListener: ClickableSpan) {
    val spannableString = SpannableStringBuilder(text)
    val clickableIndex = text.indexOf(textToLink)
    spannableString.setSpan(
        onClickListener,
        clickableIndex,
        clickableIndex + textToLink.length,
        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    text = spannableString
    movementMethod = LinkMovementMethod.getInstance()
    highlightColor = Color.TRANSPARENT
}