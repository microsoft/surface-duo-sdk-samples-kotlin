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
        PaintColors.Red -> R.color.hinge_angle_red
        PaintColors.Blue -> R.color.hinge_angle_blue
        PaintColors.Green -> R.color.hinge_angle_green
        PaintColors.Yellow -> R.color.hinge_angle_yellow
        PaintColors.Purple -> R.color.hinge_angle_purple
    }
