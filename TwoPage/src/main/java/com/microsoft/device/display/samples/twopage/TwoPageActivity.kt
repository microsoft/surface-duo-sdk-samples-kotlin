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
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.microsoft.device.display.samples.twopage.extensions.isInPortrait
import com.microsoft.device.display.samples.twopage.fragments.FirstPageFragment
import com.microsoft.device.display.samples.twopage.fragments.FourthPageFragment
import com.microsoft.device.display.samples.twopage.fragments.SecondPageFragment
import com.microsoft.device.display.samples.twopage.fragments.ThirdPageFragment
import com.microsoft.device.dualscreen.ScreenInfo
import com.microsoft.device.dualscreen.ScreenInfoListener
import com.microsoft.device.dualscreen.ScreenManagerProvider

class TwoPageActivity : AppCompatActivity(), OnPageChangeListener, ScreenInfoListener {
    private lateinit var viewPager: ViewPager
    private lateinit var pagerAdapter: PagerAdapter
    private var position = 0

    @SuppressLint("InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.two_page_activity_main)
        setupToolbar()
        setupPagerAdapter()
    }

    private fun setupToolbar() {
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onScreenInfoChanged(screenInfo: ScreenInfo) {
        pagerAdapter.showTwoPages = screenInfo.isDualMode() && !screenInfo.isInPortrait
        pagerAdapter.pageContentScrollEnabled = !screenInfo.isDualMode() || !screenInfo.isInPortrait
        setupViewPager()
    }

    override fun onResume() {
        super.onResume()
        ScreenManagerProvider.getScreenManager().addScreenInfoListener(this)
    }

    override fun onPause() {
        super.onPause()
        ScreenManagerProvider.getScreenManager().removeScreenInfoListener(this)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        ScreenManagerProvider.getScreenManager().onConfigurationChanged()
    }

    private fun setupPagerAdapter() {
        val fragments = SparseArray<Fragment>()
        fragments.put(0, FirstPageFragment())
        fragments.put(1, SecondPageFragment())
        fragments.put(2, ThirdPageFragment())
        fragments.put(3, FourthPageFragment())
        pagerAdapter = PagerAdapter(supportFragmentManager, fragments)
    }

    private fun setupViewPager() {
        if (::viewPager.isInitialized) {
            viewPager.adapter = null
        }
        viewPager = findViewById<ViewPager>(R.id.pager).also {
            it.adapter = pagerAdapter
            it.currentItem = position
            it.addOnPageChangeListener(this)
        }
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

    override fun onPageSelected(position: Int) {
        this.position = position
    }

    override fun onPageScrollStateChanged(state: Int) {}
}