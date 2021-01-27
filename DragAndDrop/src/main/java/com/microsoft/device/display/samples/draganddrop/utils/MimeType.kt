/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.draganddrop.utils

import android.content.ClipDescription

/**
 * The mime types corresponding to all data types that can be dragged and dropped
 */
enum class MimeType {
    IMAGE_JPEG,
    TEXT_PLAIN;

    companion object
}

/**
 * The corresponding [String] value
 */
val MimeType.value: String
    get() = when (this) {
        MimeType.IMAGE_JPEG -> "image/jpeg"
        MimeType.TEXT_PLAIN -> ClipDescription.MIMETYPE_TEXT_PLAIN
    }

/**
 * Creates and returns the corresponding [MimeType] for the given [String] mime type value
 *
 * @return The corresponding [MimeType]
 */
fun MimeType.Companion.fromValue(value: String?): MimeType? {
    return when {
        value == null -> null
        value.startsWith("text/") -> MimeType.TEXT_PLAIN
        value.startsWith("image/") -> MimeType.IMAGE_JPEG
        else -> null
    }
}
