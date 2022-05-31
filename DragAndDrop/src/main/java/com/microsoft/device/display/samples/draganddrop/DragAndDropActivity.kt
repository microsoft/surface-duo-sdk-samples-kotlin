/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.draganddrop

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.window.layout.WindowInfoTracker
import androidx.window.layout.WindowLayoutInfo
import com.microsoft.device.display.samples.draganddrop.databinding.ActivityDragAndDropBinding
import com.microsoft.device.display.samples.draganddrop.fragment.DragSourceFragment
import com.microsoft.device.display.samples.draganddrop.fragment.DropTargetFragment
import com.microsoft.device.dualscreen.utils.wm.isInDualMode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * The Activity containing the drag source and drop target containers
 */
class DragAndDropActivity : AppCompatActivity() {

    companion object {
        private const val FRAGMENT_SINGLE_SCREEN_DRAG_SOURCE = "FragmentSingleScreenDragSource"
        private const val FRAGMENT_SINGLE_SCREEN_DROP_TARGET = "FragmentSingleScreenDropTarget"
        private const val FRAGMENT_DUAL_SCREEN_DRAG_SOURCE = "FragmentDualScreenDragSource"
        private const val FRAGMENT_DUAL_SCREEN_DROP_TARGET = "FragmentDualScreenDropTarget"
    }

    private lateinit var binding: ActivityDragAndDropBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDragAndDropBinding.inflate(layoutInflater)
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
                WindowInfoTracker.getOrCreate(this@DragAndDropActivity)
                    .windowLayoutInfo(this@DragAndDropActivity)
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
        } else {
            setupFragmentsForSingleScreen()
        }
    }

    /**
     * Setup and adds the fragments for the single screen mode
     */
    private fun setupFragmentsForSingleScreen() {
        if (supportFragmentManager.findFragmentByTag(FRAGMENT_SINGLE_SCREEN_DRAG_SOURCE) == null) {
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.drag_source_container,
                    DragSourceFragment.newInstance(),
                    FRAGMENT_SINGLE_SCREEN_DRAG_SOURCE
                ).commit()
        }
        if (supportFragmentManager.findFragmentByTag(FRAGMENT_SINGLE_SCREEN_DROP_TARGET) == null) {
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.drop_target_container,
                    DropTargetFragment.newInstance(),
                    FRAGMENT_SINGLE_SCREEN_DROP_TARGET
                ).commit()
        }
    }

    /**
     * Setup and adds the fragments for the dual screen mode
     */
    private fun setupFragmentsForDualScreen() {
        if (supportFragmentManager.findFragmentByTag(FRAGMENT_DUAL_SCREEN_DRAG_SOURCE) == null) {
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.first_container_id,
                    DragSourceFragment.newInstance(),
                    FRAGMENT_DUAL_SCREEN_DRAG_SOURCE
                ).commit()
        }
        if (supportFragmentManager.findFragmentByTag(FRAGMENT_DUAL_SCREEN_DROP_TARGET) == null) {
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.second_container_id,
                    DropTargetFragment.newInstance(),
                    FRAGMENT_DUAL_SCREEN_DROP_TARGET
                ).commit()
        }
    }
}
