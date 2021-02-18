/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.multipleinstances

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiObject
import androidx.test.uiautomator.UiSelector
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
@LargeTest
class ShortcutsTest {

    private val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
    private val context = InstrumentationRegistry.getInstrumentation().targetContext

    private val appName = context.getString(R.string.multiple_instances_app_name)
    private val mainActivityShortcutLabel = context.getString(R.string.multiple_instances_main_shortcut_label)
    private val secondActivityShortcutLabel = context.getString(R.string.multiple_instances_second_shortcut_label)

    @Test
    fun openSecondShortcutOnRightScreen_whenMainActivityIsVisibleOnLeft() {
        longClick(getLeftAppIcon())

        if (!device.hasObject(By.text(mainActivityShortcutLabel))) {
            Assert.fail("The $mainActivityShortcutLabel shortcut was not found")
        }

        device.findObject(UiSelector().text(mainActivityShortcutLabel)).clickAndWaitForNewWindow()
        onView(withText(R.string.multiple_instances_main_activity_text)).check(matches(isDisplayed()))

        longClick(getRightAppIcon())

        if (!device.hasObject(By.text(secondActivityShortcutLabel))) {
            Assert.fail("The $secondActivityShortcutLabel shortcut was not found")
        }

        device.findObject(UiSelector().text(secondActivityShortcutLabel)).clickAndWaitForNewWindow()
        onView(withText(R.string.multiple_instances_second_activity_text)).check(matches(isDisplayed()))
    }

    @Test
    fun openMainShortcutOnRightScreen_whenSecondActivityIsVisibleOnLeft() {
        longClick(getLeftAppIcon())

        if (!device.hasObject(By.text(secondActivityShortcutLabel))) {
            Assert.fail("The $secondActivityShortcutLabel shortcut was not found")
        }

        device.findObject(UiSelector().text(secondActivityShortcutLabel)).clickAndWaitForNewWindow()
        onView(withText(R.string.multiple_instances_second_activity_text)).check(matches(isDisplayed()))

        longClick(getRightAppIcon())

        if (!device.hasObject(By.text(mainActivityShortcutLabel))) {
            Assert.fail("The $mainActivityShortcutLabel shortcut was not found")
        }

        device.findObject(UiSelector().text(mainActivityShortcutLabel)).clickAndWaitForNewWindow()
        onView(withText(R.string.multiple_instances_main_activity_text)).check(matches(isDisplayed()))
    }

    private fun longClick(target: UiObject) {
        target.dragTo(target, LONG_CLICK_STEPS)
    }

    private fun getRightAppIcon(): UiObject {
        while (!device.hasObject(By.text(appName))) {
            scrollInRightAppDrawer()
        }
        return device.findObject(UiSelector().text(appName))
    }

    private fun getLeftAppIcon(): UiObject {
        while (!device.hasObject(By.text(appName))) {
            scrollInLeftAppDrawer()
        }
        return device.findObject(UiSelector().text(appName))
    }

    private fun scrollInRightAppDrawer() {
        device.swipe(2100, 1300, 2100, 200, 50)
    }

    private fun scrollInLeftAppDrawer() {
        device.swipe(600, 1300, 600, 200, 50)
    }

    companion object {
        const val LONG_CLICK_STEPS = 100
    }
}
