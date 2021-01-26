/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.hingeangle.extensions

import android.graphics.Bitmap
import android.graphics.Matrix

/**
 * Creates a new bitmap that represents the flipped image for the current bitmap
 * @return the flipped bitmap for the current bitmap
 */
fun Bitmap.flip(): Bitmap {
    val bitmapCopy = copy(config, true)
    val matrix = Matrix().apply {
        preScale(-1.0f, 1.0f)
    }
    return Bitmap.createBitmap(bitmapCopy, 0, 0, bitmapCopy.width, bitmapCopy.height, matrix, true)
}