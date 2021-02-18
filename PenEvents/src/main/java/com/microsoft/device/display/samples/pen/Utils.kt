/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.pen

import android.view.MotionEvent

/**
 * @return The tool name for the given toolType
 */
fun parseToolType(toolType: Int): String =
    when (toolType) {
        MotionEvent.TOOL_TYPE_STYLUS -> "Pen "
        MotionEvent.TOOL_TYPE_ERASER -> "Eraser "
        MotionEvent.TOOL_TYPE_FINGER -> "Finger "
        MotionEvent.TOOL_TYPE_MOUSE -> "Mouse "
        else -> "UNKNOWN device $toolType"
    }