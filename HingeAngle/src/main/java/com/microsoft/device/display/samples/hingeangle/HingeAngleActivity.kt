/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.hingeangle

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.window.layout.WindowInfoTracker
import androidx.window.layout.WindowLayoutInfo
import com.microsoft.device.display.samples.hingeangle.databinding.ActivityHingeAngleBinding
import com.microsoft.device.display.samples.hingeangle.fragments.DualScreenFragment
import com.microsoft.device.display.samples.hingeangle.fragments.SingleScreenFragment
import com.microsoft.device.dualscreen.utils.wm.isInDualMode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HingeAngleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHingeAngleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHingeAngleBinding.inflate(layoutInflater)
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
                WindowInfoTracker.getOrCreate(this@HingeAngleActivity)
                    .windowLayoutInfo(this@HingeAngleActivity)
                    .collect { windowLayoutInfo ->
                        onWindowLayoutInfoChanged(windowLayoutInfo)
                    }
            }
        }
    }

    private fun onWindowLayoutInfoChanged(windowLayoutInfo: WindowLayoutInfo) {
        addFragments(windowLayoutInfo)
    }

    private fun addFragments(windowLayoutInfo: WindowLayoutInfo) {
        if (windowLayoutInfo.isInDualMode()) {
            addFragmentsForDualScreen()
        } else {
            addFragmentsForSingleScreen()
        }
    }

    private fun addFragmentsForDualScreen() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.first_container_id, SingleScreenFragment(), null)
            .commit()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.second_container_id, DualScreenFragment(), null)
            .commit()
    }

    private fun addFragmentsForSingleScreen() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.first_container_id, SingleScreenFragment(), null)
            .commit()
    }
}
