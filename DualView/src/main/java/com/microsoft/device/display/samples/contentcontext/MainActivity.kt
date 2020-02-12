/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */
package com.microsoft.device.display.samples.contentcontext

import android.content.res.Configuration
import android.os.Bundle
import android.view.MenuItem
import android.view.Surface
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.microsoft.device.display.samples.contentcontext.fragment.BaseFragment
import com.microsoft.device.display.samples.contentcontext.fragment.DualLandscape
import com.microsoft.device.display.samples.contentcontext.fragment.DualPortrait
import com.microsoft.device.display.samples.contentcontext.fragment.SinglePortrait
import com.microsoft.device.display.samples.contentcontext.Item.CREATOR.items
import com.microsoft.device.display.samples.utils.ScreenHelper

class MainActivity : AppCompatActivity(), BaseFragment.OnItemSelectedListener {

    private lateinit var screenHelper: ScreenHelper
    private var isDuo = false
    private lateinit var fragmentMap: MutableMap<String, BaseFragment>
    private var currentSelectedPosition = -1

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        screenHelper = ScreenHelper()
        isDuo = screenHelper.initialize(this)

        fragmentMap = HashMap<String, BaseFragment>().also { map ->
            map[SinglePortrait::class.java.simpleName] = SinglePortrait.newInstance(items).also {
                it.registerOnItemSelectedListener(this)
            }
            map[DualPortrait::class.java.simpleName] = DualPortrait.newInstance(items).also {
                it.registerOnItemSelectedListener(this)
            }
            map[DualLandscape::class.java.simpleName] = DualLandscape.newInstance(items).also {
                it.registerOnItemSelectedListener(this)
            }
        }
        setupLayout()
    }

    private fun useSingleMode() {
        fragmentMap[SinglePortrait::class.java.simpleName]?.also { showFragment(it) }
    }

    private fun useDualMode(rotation: Int) {
        when (rotation) {
            Surface.ROTATION_90, Surface.ROTATION_270 -> {
                // Setting layout for double landscape
                fragmentMap[DualLandscape::class.java.simpleName]?.also { showFragment(it) }
            }
            else -> {
                fragmentMap[DualPortrait::class.java.simpleName]?.also { showFragment(it) }
            }
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

    private fun showFragment(fragment: BaseFragment) {
        supportFragmentManager.commit {
            if (!fragment.isAdded) {
                add(R.id.activity_main, fragment)
            }
            show(fragment)
            if (currentSelectedPosition != -1) {
                fragment.setCurrentSelectedPosition(currentSelectedPosition)
            }
            for (stringBaseFragmentEntry in fragmentMap.entries) {
                if ((stringBaseFragmentEntry as Map.Entry<*, *>).value !== fragment) {
                    hide(((stringBaseFragmentEntry as Map.Entry<*, *>).value as Fragment?)!!)
                }
            }
        }
    }

    override fun onBackPressed() {
        setTitle(R.string.app_name)
        for (stringBaseFragmentEntry in fragmentMap.entries) {
            ((stringBaseFragmentEntry as Map.Entry<*, *>).value as BaseFragment).apply {
                if (isVisible) {
                    if (onBackPressed()) {
                        finish()
                    }
                }
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

    override fun onItemSelected(position: Int) {
        currentSelectedPosition = position
    }
}