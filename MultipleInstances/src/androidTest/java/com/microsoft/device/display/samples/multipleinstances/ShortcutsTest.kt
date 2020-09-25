/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.multipleinstances

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
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

    private val APP_NAME = context.getString(R.string.app_name)
    private val NEW_INSTANCE_SHORTCUT_LABEL = context.getString(R.string.main_shortcut_label)
    private val SECOND_ACTIVITY_SHORTCUT_LABEL = context.getString(R.string.second_shortcut_label)

    @Test
    fun openSecondShortcutOnRightScreen_whenMainActivityIsVisibleOnLeft() {
        longClick(getLeftAppIcon())

        if (!device.hasObject(By.text(NEW_INSTANCE_SHORTCUT_LABEL))) {
            Assert.fail("The $NEW_INSTANCE_SHORTCUT_LABEL shortcut was not found")
        }

        device.findObject(UiSelector().text(NEW_INSTANCE_SHORTCUT_LABEL)).clickAndWaitForNewWindow()
        onView(withId(R.id.main_text)).check(matches(isDisplayed()))

        longClick(getRightAppIcon())

        if (!device.hasObject(By.text(SECOND_ACTIVITY_SHORTCUT_LABEL))) {
            Assert.fail("The $SECOND_ACTIVITY_SHORTCUT_LABEL shortcut was not found")
        }

        device.findObject(UiSelector().text(SECOND_ACTIVITY_SHORTCUT_LABEL)).clickAndWaitForNewWindow()
        onView(withId(R.id.second_text)).check(matches(isDisplayed()))
    }

    @Test
    fun openMainShortcutOnRightScreen_whenSecondActivityIsVisibleOnLeft() {
        longClick(getLeftAppIcon())

        if (!device.hasObject(By.text(SECOND_ACTIVITY_SHORTCUT_LABEL))) {
            Assert.fail("The $SECOND_ACTIVITY_SHORTCUT_LABEL shortcut was not found")
        }

        device.findObject(UiSelector().text(SECOND_ACTIVITY_SHORTCUT_LABEL)).clickAndWaitForNewWindow()
        onView(withId(R.id.second_text)).check(matches(isDisplayed()))

        longClick(getRightAppIcon())

        if (!device.hasObject(By.text(NEW_INSTANCE_SHORTCUT_LABEL))) {
            Assert.fail("The $NEW_INSTANCE_SHORTCUT_LABEL shortcut was not found")
        }

        device.findObject(UiSelector().text(NEW_INSTANCE_SHORTCUT_LABEL)).clickAndWaitForNewWindow()
        onView(withId(R.id.main_text)).check(matches(isDisplayed()))
    }

    private fun longClick(target: UiObject) {
        target.dragTo(target, LONG_CLICK_STEPS)
    }

    private fun getRightAppIcon(): UiObject {
        while (!device.hasObject(By.text(APP_NAME))) {
            scrollInRightAppDrawer()
        }
        return device.findObject(UiSelector().text(APP_NAME))
    }

    private fun getLeftAppIcon(): UiObject {
        while (!device.hasObject(By.text(APP_NAME))) {
            scrollInLeftAppDrawer()
        }
        return device.findObject(UiSelector().text(APP_NAME))
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
