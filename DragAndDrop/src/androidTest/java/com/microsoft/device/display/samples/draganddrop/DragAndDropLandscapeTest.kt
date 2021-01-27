/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.draganddrop

import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.microsoft.device.display.samples.draganddrop.utils.setOrientationLeft
import com.microsoft.device.display.samples.draganddrop.utils.setOrientationRight
import com.microsoft.device.display.samples.draganddrop.utils.unfreezeRotation
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
@LargeTest
class DragAndDropLandscapeTest : DragAndDropPortraitTest() {
    @After
    fun tearDown() {
        unfreezeRotation()
    }

    @Test
    fun dragAndDropImageInSingleModeWithOrientationLeft() {
        setOrientationLeft()
        dragAndDropImageInSingleMode()
    }

    @Test
    fun dragAndDropImageInSingleModeWithOrientationRight() {
        setOrientationRight()
        dragAndDropImageInSingleMode()
    }

    @Test
    fun dragAndDropTextInSingleModeWithOrientationLeft() {
        setOrientationLeft()
        dragAndDropTextInSingleMode()
    }

    @Test
    fun dragAndDropTextInSingleModeWithOrientationRight() {
        setOrientationRight()
        dragAndDropTextInSingleMode()
    }

    @Test
    fun dragAndDropImageInDualModeWithOrientationLeft() {
        setOrientationLeft()
        dragAndDropImageInDualMode()
    }

    @Test
    fun dragAndDropImageInDualModeWithOrientationRight() {
        setOrientationRight()
        dragAndDropImageInDualMode()
    }

    @Test
    fun dragAndDropTextInDualModeWithOrientationLeft() {
        setOrientationLeft()
        dragAndDropTextInDualMode()
    }

    @Test
    fun dragAndDropTextInDualModeWithOrientationRight() {
        setOrientationRight()
        dragAndDropTextInDualMode()
    }
}
