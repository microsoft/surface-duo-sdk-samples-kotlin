/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.multipleinstances

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.window.layout.WindowInfoRepository.Companion.windowInfoRepository
import androidx.window.layout.WindowLayoutInfo
import com.microsoft.device.display.samples.multipleinstances.databinding.ActivityMultipleInstancesBinding
import com.microsoft.device.dualscreen.utils.wm.isInDualMode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * Base Activity that shows one TextView for single screen mode and two TextView's for dual screen mode.
 */
abstract class MultipleInstancesBaseActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMultipleInstancesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMultipleInstancesBinding.inflate(layoutInflater)
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
                    binding.foldableLayout.postDelayed(
                        { onWindowLayoutInfoChanged(info) },
                        200L
                    )
                }
            }
        }
    }

    private fun onWindowLayoutInfoChanged(windowLayoutInfo: WindowLayoutInfo) {
        findViewById<AppCompatTextView>(R.id.first_screen_text).text = getFirstScreenText()

        if (windowLayoutInfo.isInDualMode()) {
            findViewById<AppCompatTextView>(R.id.second_screen_text).text = getSecondScreenText()
        }
    }

    /**
     * @return the text for the first screen TextView
     */
    abstract fun getFirstScreenText(): String

    /**
     * @return the text for the second screen TextView
     */
    abstract fun getSecondScreenText(): String
}