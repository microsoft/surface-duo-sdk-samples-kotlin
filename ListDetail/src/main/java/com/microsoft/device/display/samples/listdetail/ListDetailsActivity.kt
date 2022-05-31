/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.listdetail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.window.layout.WindowInfoTracker
import androidx.window.layout.WindowLayoutInfo
import com.microsoft.device.display.samples.listdetail.databinding.ActivityListDetailsBinding
import com.microsoft.device.display.samples.listdetail.extensions.TAG
import com.microsoft.device.display.samples.listdetail.extensions.isBackStackEmpty
import com.microsoft.device.display.samples.listdetail.model.SelectionViewModel
import com.microsoft.device.dualscreen.utils.wm.isFoldingFeatureVertical
import com.microsoft.device.dualscreen.utils.wm.isInDualMode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * Contains the image gallery and selected image details but only in dual screen mode.
 */
class ListDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListDetailsBinding
    private var windowLayoutInfo: WindowLayoutInfo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        observeSelectedItem()
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

    override fun onBackPressed() {
        if (supportFragmentManager.isBackStackEmpty) {
            finish()
        } else {
            supportFragmentManager.popBackStack()
        }
    }

    private fun observeSelectedItem() {
        val viewModel = ViewModelProvider(this).get(SelectionViewModel::class.java)
        viewModel.selectedItem.observe(this) {
            windowLayoutInfo?.let {
                if (it.isInDualMode() && it.isFoldingFeatureVertical()) {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.second_container_id, ImageDetailsFragment(), null)
                        .commit()
                } else {
                    val detailsFragment = ImageDetailsFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .add(R.id.first_container_id, detailsFragment, detailsFragment.TAG)
                        .addToBackStack(detailsFragment.TAG)
                        .commit()
                }
            }
        }
    }

    private fun registerWindowInfoFlow() {
        lifecycleScope.launch(Dispatchers.Main) {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                WindowInfoTracker.getOrCreate(this@ListDetailsActivity)
                    .windowLayoutInfo(this@ListDetailsActivity)
                    .collect { windowLayoutInfo ->
                        setupLayout(windowLayoutInfo)
                    }
            }
        }
    }

    private fun setupLayout(windowLayoutInfo: WindowLayoutInfo) {
        this.windowLayoutInfo = windowLayoutInfo
        supportFragmentManager.popBackStack()
        if (windowLayoutInfo.isInDualMode() && windowLayoutInfo.isFoldingFeatureVertical()) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.first_container_id, ImagesFragment(), null)
                .commit()
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.second_container_id, ImageDetailsFragment(), null)
                .commit()
        } else {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.first_container_id, ImagesFragment(), null)
                .commit()
        }
    }
}