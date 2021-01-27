/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.draganddrop

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.microsoft.device.display.samples.draganddrop.fragment.DragSourceFragment
import com.microsoft.device.display.samples.draganddrop.fragment.DropTargetFragment
import com.microsoft.device.dualscreen.ScreenInfo
import com.microsoft.device.dualscreen.ScreenInfoListener
import com.microsoft.device.dualscreen.ScreenManagerProvider
import kotlinx.android.synthetic.main.drag_and_drop_main_activity.*

/**
 * The Activity containing the drag source and drop target containers
 */
class DragAndDropActivity : AppCompatActivity(), ScreenInfoListener {

    companion object {
        private const val FRAGMENT_SINGLE_SCREEN_DRAG_SOURCE = "FragmentSingleScreenDragSource"
        private const val FRAGMENT_SINGLE_SCREEN_DROP_TARGET = "FragmentSingleScreenDropTarget"
        private const val FRAGMENT_DUAL_SCREEN_DRAG_SOURCE = "FragmentDualScreenDragSource"
        private const val FRAGMENT_DUAL_SCREEN_DROP_TARGET = "FragmentDualScreenDropTarget"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.drag_and_drop_main_activity)
        setupToolbar()
        reset_button.setOnClickListener { recreate() }
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
        setupFragments(screenInfo)
    }

    override fun onResume() {
        super.onResume()
        ScreenManagerProvider.getScreenManager().addScreenInfoListener(this)
    }

    override fun onPause() {
        super.onPause()
        ScreenManagerProvider.getScreenManager().removeScreenInfoListener(this)
    }

    /**
     * Setup and adds fragments to the screen
     */
    private fun setupFragments(screenInfo: ScreenInfo) {
        if (screenInfo.isDualMode()) {
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
