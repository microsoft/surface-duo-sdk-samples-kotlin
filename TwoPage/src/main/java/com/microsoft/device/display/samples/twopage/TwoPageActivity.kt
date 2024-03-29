/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.twopage

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.SparseArray
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import androidx.window.layout.WindowInfoTracker
import androidx.window.layout.WindowLayoutInfo
import com.microsoft.device.display.samples.twopage.databinding.ActivityTwoPageBinding
import com.microsoft.device.display.samples.twopage.fragments.FirstPageFragment
import com.microsoft.device.display.samples.twopage.fragments.FourthPageFragment
import com.microsoft.device.display.samples.twopage.fragments.SecondPageFragment
import com.microsoft.device.display.samples.twopage.fragments.ThirdPageFragment
import com.microsoft.device.dualscreen.utils.wm.isFoldingFeatureVertical
import com.microsoft.device.dualscreen.utils.wm.isInDualMode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class TwoPageActivity : AppCompatActivity(), OnPageChangeListener {
    private lateinit var binding: ActivityTwoPageBinding
    private var position = 0

    @SuppressLint("InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTwoPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        registerWindowInfoFlow()
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

    private fun registerWindowInfoFlow() {
        lifecycleScope.launch(Dispatchers.Main) {
            lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                WindowInfoTracker.getOrCreate(this@TwoPageActivity)
                    .windowLayoutInfo(this@TwoPageActivity)
                    .collect { info ->
                        onWindowLayoutInfoChanged(info)
                    }
            }
        }
    }

    private fun onWindowLayoutInfoChanged(windowLayoutInfo: WindowLayoutInfo) {
        binding.pager.also { pager ->
            if (pager.adapter == null) {
                val adapter = setupPagerAdapter()
                adapter.showTwoPages =
                    windowLayoutInfo.isInDualMode() && windowLayoutInfo.isFoldingFeatureVertical()
                adapter.pageContentScrollEnabled =
                    !windowLayoutInfo.isInDualMode() || !windowLayoutInfo.isFoldingFeatureVertical()
                pager.adapter = adapter
            }
        }
    }

    private fun setupPagerAdapter(): PagerAdapter {
        val fragments = SparseArray<Fragment>()
        fragments.put(0, FirstPageFragment())
        fragments.put(1, SecondPageFragment())
        fragments.put(2, ThirdPageFragment())
        fragments.put(3, FourthPageFragment())
        return PagerAdapter(supportFragmentManager, fragments)
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

    override fun onPageSelected(position: Int) {
        this.position = position
    }

    override fun onPageScrollStateChanged(state: Int) {}
}