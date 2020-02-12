/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.complementarycontext

import android.content.res.Configuration
import android.os.Bundle
import android.view.Surface
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import com.microsoft.device.display.samples.complementarycontext.fragment.BaseFragment
import com.microsoft.device.display.samples.complementarycontext.fragment.DualLandscape
import com.microsoft.device.display.samples.complementarycontext.fragment.DualPortrait
import com.microsoft.device.display.samples.complementarycontext.fragment.SinglePortrait
import com.microsoft.device.display.samples.utils.ScreenHelper

class MainActivity : AppCompatActivity(), BaseFragment.OnItemSelectedListener {

    private lateinit var singlePortrait: SinglePortrait
    private lateinit var dualPortrait: DualPortrait
    private lateinit var dualLandscape: DualLandscape
    private lateinit var screenHelper: ScreenHelper
    private var isDuo = false
    private var currentPosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        screenHelper = ScreenHelper().also {
            isDuo = it.initialize(this)
        }

        currentPosition = 0
        val slides = Slide.slides

        singlePortrait = SinglePortrait.newInstance(slides).also {
            it.registerOnItemSelectedListener(this)
        }

        dualPortrait = DualPortrait.newInstance(slides).also {
            it.registerOnItemSelectedListener(this)
        }

        dualLandscape = DualLandscape.newInstance(slides).also {
            it.registerOnItemSelectedListener(this)
        }

        setupLayout()
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.commit {
            val showFragment = supportFragmentManager.findFragmentById(R.id.activity_main)
            if (showFragment == null) {
                add(R.id.activity_main, fragment)
            } else {
                remove(showFragment)
                add(R.id.activity_main, fragment)
            }
        }
    }

    private fun hideFragment(fragment: Fragment) {
        supportFragmentManager.commit {
            if (fragment.isAdded) {
                val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
                fragmentTransaction.hide(fragment)
            }
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        setupLayout()
    }

    private fun useDualMode(rotation: Int) {
        if (rotation == Surface.ROTATION_90 || rotation == Surface.ROTATION_270) {
            dualLandscape.setCurrentPosition(currentPosition)
            showFragment(dualLandscape)
            hideFragment(dualPortrait)
            hideFragment(singlePortrait)
        } else {
            dualPortrait.setCurrentPosition(currentPosition)
            showFragment(dualPortrait)
            hideFragment(singlePortrait)
            hideFragment(dualLandscape)
        }
    }

    private fun useSingleMode() {
        singlePortrait.setCurrentPosition(currentPosition)
        showFragment(singlePortrait)
        hideFragment(dualLandscape)
        hideFragment(dualPortrait)
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

    override fun onItemSelected(position: Int) {
        currentPosition = position
    }
}
