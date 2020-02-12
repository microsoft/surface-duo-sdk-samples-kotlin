/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */
package com.microsoft.device.display.samples.utils

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.view.WindowManager
import com.microsoft.device.display.DisplayMask
class ScreenHelper {
    private lateinit var mDisplayMask: DisplayMask
    private lateinit var mActivity: Activity

    private val rotation: Int
        get() = getRotation(mActivity)

    private val windowRect: Rect
        get() {
            val windowRect = Rect()
            mActivity.windowManager.defaultDisplay.getRectSize(windowRect)
            return windowRect
        }

    fun initialize(activity: Activity): Boolean {
        try {
            mActivity = activity
            mDisplayMask = DisplayMask.fromResourcesRectApproximation(mActivity)
        } catch (ex: NoSuchMethodError) {
            ex.printStackTrace()
            return false
        } catch (ex: RuntimeException) {
            ex.printStackTrace()
            return false
        } catch (ex: NoClassDefFoundError) {
            ex.printStackTrace()
            return false
        }

        return true
    }

    private fun getHinge(rotation: Int): Rect {
        // Hinge's coordinates of its 4 edges in different mode
        // Double Landscape Rect(0, 1350 - 1800, 1434)
        // Double Portrait  Rect(1350, 0 - 1434, 1800)
        val boundings = mDisplayMask.getBoundingRectsForRotation(rotation)
        return boundings[0]
    }

    private fun getScreenRects(windowRect: Rect, hinge: Rect, screenRect1: Rect, screenRect2: Rect) {
        // Hinge's coordinates of its 4 edges in different mode
        // Double Landscape Rect(0, 1350 - 1800, 1434)
        // Double Portrait  Rect(1350, 0 - 1434, 1800)
        if (hinge.left > 0) {
            screenRect1.left = 0
            screenRect1.right = hinge.left
            screenRect1.top = 0
            screenRect1.bottom = windowRect.bottom
            screenRect2.left = hinge.right
            screenRect2.right = windowRect.right
            screenRect2.top = 0
            screenRect2.bottom = windowRect.bottom
        } else {
            screenRect1.left = 0
            screenRect1.right = windowRect.right
            screenRect1.top = 0
            screenRect1.bottom = hinge.top
            screenRect2.left = 0
            screenRect2.right = windowRect.right
            screenRect2.top = hinge.bottom
            screenRect2.bottom = windowRect.bottom
        }
    }

    fun getScreenRects(screenRect1: Rect, screenRect2: Rect, rotation: Int) {
        val hinge = getHinge(rotation)
        val windowRect = windowRect
        getScreenRects(windowRect, hinge, screenRect1, screenRect2)
    }

    companion object {

        fun getRotation(activity: Activity): Int {
            val wm = activity.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            return wm.defaultDisplay.rotation
        }
    }

    // The windowRect doesn't intersect hinge
    val isDualMode: Boolean
        get() {
            val rotation = rotation
            val hinge = getHinge(rotation)
            val windowRect = windowRect

            return if (windowRect.width() > 0 && windowRect.height() > 0)
                hinge.intersect(windowRect)
            else false
        }
}
