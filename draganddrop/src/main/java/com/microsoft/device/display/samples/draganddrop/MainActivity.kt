/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.draganddrop

import android.content.res.Configuration
import android.os.Bundle
import android.view.Surface
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

import com.microsoft.device.display.samples.draganddrop.fragment.AdaptiveFragment
import com.microsoft.device.display.samples.utils.ScreenHelper
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var screenHelper: ScreenHelper
    private var isDuo: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        screenHelper = ScreenHelper()
        isDuo = screenHelper.initialize(this)

        setupLayout()

        fab.setOnClickListener { recreate() }
    }

    private fun setupLayout() {
        val bundle = Bundle()
        val rotation = ScreenHelper.getRotation(this)

        if (isDuo && screenHelper.isDualMode) {
            when (rotation) {
                Surface.ROTATION_90, Surface.ROTATION_270 ->
                    // Setting layout for double landscape
                    bundle.putInt(AdaptiveFragment.KEY_LAYOUT_ID, R.layout.fragment_dual_landscape)
                else -> bundle.putInt(AdaptiveFragment.KEY_LAYOUT_ID, R.layout.fragment_dual_portrail)
            }
        } else {
            bundle.putInt(AdaptiveFragment.KEY_LAYOUT_ID, R.layout.fragment_single_portrait)
        }

        val adaptiveFragment = AdaptiveFragment.newInstance()
        adaptiveFragment.arguments = bundle
        showFragment(adaptiveFragment)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        setupLayout()
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.activity_main, fragment)
            .commit()
    }
}
