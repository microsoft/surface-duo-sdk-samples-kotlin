/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.draganddrop

import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
@LargeTest
open class DragAndDropPortraitTest : BaseDragAndDropTest() {
    @Test
    fun dragAndDropImageInSingleMode() {
        checkDragAndDropImageInSingleMode()
    }

    @Test
    fun dragAndDropTextInSingleMode() {
        checkDragAndDropTextInSingleMode()
    }

    @Test
    fun dragAndDropImageInDualMode() {
        checkDragAndDropImageInDualMode()
    }

    @Test
    fun dragAndDropTextInDualMode() {
        checkDragAndDropTextInDualMode()
    }
}
