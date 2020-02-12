/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */
package com.microsoft.device.display.samples.twopage

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Bundle
import android.view.Surface
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.microsoft.device.display.samples.utils.ScreenHelper

class MainActivity : AppCompatActivity(), OnPageChangeListener {
    private lateinit var viewPager: ViewPager
    private lateinit var pagerAdapter: PagerAdapter
    private lateinit var screenHelper: ScreenHelper
    private var position = 0
    private var isDuo = false
    private var showTwoPages = false
    private lateinit var single: View
    private lateinit var dual: View
    @SuppressLint("InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pagerAdapter = PagerAdapter(supportFragmentManager, TestFragment.fragments)
        screenHelper = ScreenHelper().also {
            isDuo = it.initialize(this)
        }
        single = layoutInflater.inflate(R.layout.activity_main, null)
        dual = layoutInflater.inflate(R.layout.double_landscape_layout, null)
        setupLayout()
    }

    private fun useSingleMode() {
        // Setting layout for single portrait
        setContentView(single)
        showTwoPages = false
        setupViewPager()
    }

    private fun useDualMode(rotation: Int) {
        showTwoPages = when (rotation) {
            Surface.ROTATION_90, Surface.ROTATION_270 -> {
                // Setting layout for double landscape
                setContentView(dual)
                false
            }
            else -> {
                // Setting layout for double portrait
                setContentView(single)
                true
            }
        }
        setupViewPager()
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

    private fun setupViewPager() {
        pagerAdapter.showTwoPages(showTwoPages)
        if (::viewPager.isInitialized) {
            viewPager.adapter = null
        }
        viewPager = findViewById<ViewPager>(R.id.pager).also {
            it.adapter = pagerAdapter
            it.setCurrentItem(position)
            it.addOnPageChangeListener(this)
        }
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) { //
    }

    override fun onPageSelected(position: Int) {
        this.position = position
    }

    override fun onPageScrollStateChanged(state: Int) { //
    }
}