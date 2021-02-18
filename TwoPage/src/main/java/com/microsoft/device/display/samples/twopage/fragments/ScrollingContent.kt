/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.twopage.fragments

/**
 * Interface defining methods used to enable or disable scrolling
 */
interface ScrollingContent {
    /**
     * Implement this method in order to disable or enable the scroll content
     * @param enabled [true] means that the scroll should be enabled, [false] otherwise
     */
    fun enableScroll(enabled: Boolean)
}