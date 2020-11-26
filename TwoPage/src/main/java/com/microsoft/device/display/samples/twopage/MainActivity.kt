/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */
package com.microsoft.device.display.samples.twopage

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Bundle
import android.util.SparseArray
import android.view.Surface
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.microsoft.device.display.samples.twopage.fragments.FirstPageFragment
import com.microsoft.device.display.samples.twopage.fragments.FourthPageFragment
import com.microsoft.device.display.samples.twopage.fragments.SecondPageFragment
import com.microsoft.device.display.samples.twopage.fragments.ThirdPageFragment
import com.microsoft.device.dualscreen.core.ScreenHelper

class MainActivity : AppCompatActivity(), OnPageChangeListener {
    private lateinit var viewPager: ViewPager
    private lateinit var pagerAdapter: PagerAdapter
    private var position = 0
    private var showTwoPages = false
    private lateinit var single: View
    private lateinit var dual: View
    @SuppressLint("InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()

        setupPager()
        single = layoutInflater.inflate(R.layout.activity_main, null)
        dual = layoutInflater.inflate(R.layout.double_landscape_layout, null)
        setupLayout()
    }

    private fun setupPager() {
        val fragments = SparseArray<Fragment>()
        fragments.put(0, FirstPageFragment())
        fragments.put(1, SecondPageFragment())
        fragments.put(2, ThirdPageFragment())
        fragments.put(3, FourthPageFragment())
        pagerAdapter = PagerAdapter(supportFragmentManager, fragments)
    }

    private fun setupLayout() {
        if (ScreenHelper.isDualMode(this)) {
            useDualMode()
        } else {
            useSingleMode()
        }
    }

    private fun useDualMode() {
        val rotation = ScreenHelper.getCurrentRotation(this)
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

    private fun useSingleMode() { // Setting layout for single portrait
        setContentView(single)
        showTwoPages = false
        setupViewPager()
    }

    private fun setupViewPager() {
        pagerAdapter.showTwoPages(showTwoPages)
        if (::viewPager.isInitialized) {
            viewPager.adapter = null
        }
        viewPager = findViewById<ViewPager>(R.id.pager).also {
            it.adapter = pagerAdapter
            it.currentItem = position
            it.addOnPageChangeListener(this)
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        setupLayout()
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

    override fun onPageSelected(position: Int) {
        this.position = position
    }

    override fun onPageScrollStateChanged(state: Int) {}
}