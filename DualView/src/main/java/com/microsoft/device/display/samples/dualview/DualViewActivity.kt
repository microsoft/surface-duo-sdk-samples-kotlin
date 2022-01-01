/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.dualview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.window.layout.WindowInfoRepository.Companion.windowInfoRepository
import androidx.window.layout.WindowLayoutInfo
import com.microsoft.device.display.samples.dualview.databinding.ActivityDualViewBinding
import com.microsoft.device.display.samples.dualview.fragments.DualViewMapFragment
import com.microsoft.device.display.samples.dualview.fragments.DualViewRestaurantsFragment
import com.microsoft.device.dualscreen.utils.wm.isInDualMode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * [AppCompatActivity] implementation that contains the restaurants screen and the map screen
 */
class DualViewActivity : AppCompatActivity() {
    companion object {
        private const val FRAGMENT_DUAL_START = "FragmentDualStart"
        private const val FRAGMENT_DUAL_END = "FragmentDualEnd"
        private const val FRAGMENT_SINGLE_SCREEN = "FragmentSingleScreen"
    }

    private lateinit var binding: ActivityDualViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDualViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        initWindowLayoutInfo()
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

    private fun initWindowLayoutInfo() {
        val windowInfoRepository = windowInfoRepository()
        lifecycleScope.launch(Dispatchers.Main) {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                windowInfoRepository.windowLayoutInfo.collect { info ->
                    onWindowLayoutInfoChanged(info)
                }
            }
        }
    }

    private fun onWindowLayoutInfoChanged(windowLayoutInfo: WindowLayoutInfo) {
        if (windowLayoutInfo.isInDualMode()) {
            setupDualScreenFragments()
        } else {
            setupSingleScreenFragments()
        }
    }

    /**
     * Setup and adds fragments for single screen mode
     */
    private fun setupSingleScreenFragments() {
        if (supportFragmentManager.findFragmentByTag(FRAGMENT_SINGLE_SCREEN) == null) {
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.first_container_id,
                    DualViewRestaurantsFragment(),
                    FRAGMENT_SINGLE_SCREEN
                )
                .commit()
        }
    }

    /**
     * Setup and adds fragments for dual screen mode
     */
    private fun setupDualScreenFragments() {
        if (supportFragmentManager.findFragmentByTag(FRAGMENT_DUAL_START) == null &&
            supportFragmentManager.findFragmentByTag(FRAGMENT_DUAL_END) == null
        ) {
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.first_container_id,
                    DualViewRestaurantsFragment(),
                    FRAGMENT_DUAL_START
                )
                .commit()

            supportFragmentManager.beginTransaction()
                .replace(R.id.second_container_id, DualViewMapFragment(), FRAGMENT_DUAL_END)
                .commit()
        }
    }
}
