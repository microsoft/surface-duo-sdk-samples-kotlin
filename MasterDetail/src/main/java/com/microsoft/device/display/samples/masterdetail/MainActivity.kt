/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.masterdetail

import android.content.res.Configuration
import android.os.Bundle
import android.view.MenuItem
import android.view.Surface
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.microsoft.device.display.samples.masterdetail.fragment.DualPortrait
import com.microsoft.device.display.samples.masterdetail.fragment.SinglePortrait
import com.microsoft.device.display.samples.utils.ScreenHelper

class MainActivity : AppCompatActivity() {

    private lateinit var screenHelper: ScreenHelper
    private var isDuo: Boolean = false
    private lateinit var singlePortrait: SinglePortrait
    private lateinit var dualPortrait: DualPortrait

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        screenHelper = ScreenHelper().also {
            isDuo = it.initialize(this)
        }
        singlePortrait = SinglePortrait.newInstance(Item.items)
        dualPortrait = DualPortrait.newInstance(Item.items)
        setupLayout()
    }

    private fun useSingleMode() {
        showFragment(singlePortrait)
    }

    private fun useDualMode(rotation: Int) {
        when (rotation) {
            Surface.ROTATION_90, Surface.ROTATION_270 ->
                // Setting layout for double landscape
                useSingleMode()
            else -> showFragment(dualPortrait)
        }
    }

    private fun setupLayout() {
        val rotation = ScreenHelper.getRotation(this)
        if (isDuo) {
            if (screenHelper.isDualMode) {
                useDualMode(rotation)
            } else {
                useSingleMode()
            }
        } else {
            useSingleMode()
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        setupLayout()
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.commit {
            if (!fragment.isAdded) {
                add(R.id.activity_main, fragment)
            }
            if (fragment is SinglePortrait) {
                hide(dualPortrait)
                show(singlePortrait)
                singlePortrait.currentSelectedPosition = dualPortrait.currentSelectedPosition
            } else {
                show(dualPortrait)
                hide(singlePortrait)
                dualPortrait.currentSelectedPosition = singlePortrait.currentSelectedPosition
            }
        }
    }

    override fun onBackPressed() {
        if (singlePortrait.isVisible) {
            if (singlePortrait.onBackPressed()) {
                this.finish()
            }
        } else {
            if (dualPortrait.onBackPressed()) {
                this.finish()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}