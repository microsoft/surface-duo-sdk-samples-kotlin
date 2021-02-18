/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.draganddrop

import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.microsoft.device.display.samples.test.utils.setOrientationLeft
import com.microsoft.device.display.samples.test.utils.setOrientationRight
import com.microsoft.device.display.samples.test.utils.unfreezeRotation
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
@LargeTest
class DragAndDropLandscapeTest : BaseDragAndDropTest() {
    @After
    fun tearDown() {
        unfreezeRotation()
    }

    @Test
    fun dragAndDropImageInSingleModeWithOrientationLeft() {
        setOrientationLeft()
        checkDragAndDropImageInSingleMode()
    }

    @Test
    fun dragAndDropImageInSingleModeWithOrientationRight() {
        setOrientationRight()
        checkDragAndDropImageInSingleMode()
    }

    @Test
    fun dragAndDropTextInSingleModeWithOrientationLeft() {
        setOrientationLeft()
        checkDragAndDropTextInSingleMode()
    }

    @Test
    fun dragAndDropTextInSingleModeWithOrientationRight() {
        setOrientationRight()
        checkDragAndDropTextInSingleMode()
    }

    @Test
    fun dragAndDropImageInDualModeWithOrientationLeft() {
        setOrientationLeft()
        checkDragAndDropImageInDualMode()
    }

    @Test
    fun dragAndDropImageInDualModeWithOrientationRight() {
        setOrientationRight()
        checkDragAndDropImageInDualMode()
    }

    @Test
    fun dragAndDropTextInDualModeWithOrientationLeft() {
        setOrientationLeft()
        checkDragAndDropTextInDualMode()
    }

    @Test
    fun dragAndDropTextInDualModeWithOrientationRight() {
        setOrientationRight()
        checkDragAndDropTextInDualMode()
    }
}
