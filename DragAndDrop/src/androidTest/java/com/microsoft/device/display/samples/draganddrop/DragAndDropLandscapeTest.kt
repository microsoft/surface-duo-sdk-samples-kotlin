/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.draganddrop

import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import com.microsoft.device.dualscreen.testing.resetOrientation
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
@LargeTest
class DragAndDropLandscapeTest : BaseDragAndDropTest() {
    private val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

    @After
    fun tearDown() {
        device.resetOrientation()
    }

    @Test
    fun dragAndDropImageInSingleModeWithOrientationLeft() {
        device.setOrientationLeft()
        checkDragAndDropImageInSingleMode()
    }

    @Test
    fun dragAndDropImageInSingleModeWithOrientationRight() {
        device.setOrientationRight()
        checkDragAndDropImageInSingleMode()
    }

    @Test
    fun dragAndDropTextInSingleModeWithOrientationLeft() {
        device.setOrientationLeft()
        checkDragAndDropTextInSingleMode()
    }

    @Test
    fun dragAndDropTextInSingleModeWithOrientationRight() {
        device.setOrientationRight()
        checkDragAndDropTextInSingleMode()
    }

    @Test
    fun dragAndDropImageInDualModeWithOrientationLeft() {
        device.setOrientationLeft()
        checkDragAndDropImageInDualMode()
    }

    @Test
    fun dragAndDropImageInDualModeWithOrientationRight() {
        device.setOrientationRight()
        checkDragAndDropImageInDualMode()
    }

    @Test
    fun dragAndDropTextInDualModeWithOrientationLeft() {
        device.setOrientationLeft()
        checkDragAndDropTextInDualMode()
    }

    @Test
    fun dragAndDropTextInDualModeWithOrientationRight() {
        device.setOrientationRight()
        checkDragAndDropTextInDualMode()
    }
}
