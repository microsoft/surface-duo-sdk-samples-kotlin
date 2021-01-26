/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.hingeangle.model

import com.microsoft.device.display.samples.hingeangle.R

enum class PaintColors {
    Red,
    Blue,
    Green,
    Yellow,
    Purple
}

val PaintColors.color: Int
    get() = when (this) {
        PaintColors.Red -> R.color.red
        PaintColors.Blue -> R.color.blue
        PaintColors.Green -> R.color.green
        PaintColors.Yellow -> R.color.yellow
        PaintColors.Purple -> R.color.purple
    }
