/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.duosamples.about

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.window.layout.WindowInfoTracker
import androidx.window.layout.WindowLayoutInfo
import com.microsoft.device.display.samples.duosamples.R
import com.microsoft.device.display.samples.duosamples.about.fragments.AboutLicensesFragment
import com.microsoft.device.display.samples.duosamples.about.fragments.AboutTeamFragment
import com.microsoft.device.display.samples.duosamples.databinding.ActivityAboutBinding
import com.microsoft.device.dualscreen.utils.wm.isInDualMode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AboutActivity : AppCompatActivity() {

    companion object {
        private const val FRAGMENT_DUAL_SCREEN_START = "AboutTeamFragment"
        private const val FRAGMENT_DUAL_SCREEN_END = "AboutLicensesFragment"
    }

    private lateinit var binding: ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutBinding.inflate(layoutInflater)
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
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                WindowInfoTracker.getOrCreate(this@AboutActivity)
                    .windowLayoutInfo(this@AboutActivity)
                    .collect { windowLayoutInfo ->
                        setupFragments(windowLayoutInfo)
                    }
            }
        }
    }

    /**
     * Setup and adds fragments to the screen
     */
    private fun setupFragments(windowLayoutInfo: WindowLayoutInfo) {
        if (windowLayoutInfo.isInDualMode()) {
            setupFragmentsForDualScreen()
        }
    }

    /**
     * Setup and adds the fragments for the dual screen mode
     */
    private fun setupFragmentsForDualScreen() {
        if (supportFragmentManager.findFragmentByTag(FRAGMENT_DUAL_SCREEN_START) == null) {
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.first_container_id,
                    AboutTeamFragment.newInstance(),
                    FRAGMENT_DUAL_SCREEN_START
                ).commit()
        }
        if (supportFragmentManager.findFragmentByTag(FRAGMENT_DUAL_SCREEN_END) == null) {
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.second_container_id,
                    AboutLicensesFragment.newInstance(),
                    FRAGMENT_DUAL_SCREEN_END
                ).commit()
        }
    }
}
