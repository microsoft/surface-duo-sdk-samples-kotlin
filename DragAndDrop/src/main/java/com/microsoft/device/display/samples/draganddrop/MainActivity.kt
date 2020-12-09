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
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), ScreenInfoListener {

    companion object {
        private const val FRAGMENT_SINGLE_SCREEN_DRAG_SOURCE = "FragmentSingleScreenDragSource"
        private const val FRAGMENT_SINGLE_SCREEN_DROP_TARGET = "FragmentSingleScreenDropTarget"
        private const val FRAGMENT_DUAL_SCREEN_DRAG_SOURCE = "FragmentDualScreenDragSource"
        private const val FRAGMENT_DUAL_SCREEN_DROP_TARGET = "FragmentDualScreenDropTarget"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        reset_button.setOnClickListener { recreate() }
    }

    override fun onScreenInfoChanged(screenInfo: ScreenInfo) {
        setupLayout(screenInfo)
    }

    override fun onResume() {
        super.onResume()
        ScreenManagerProvider.getScreenManager().addScreenInfoListener(this)
    }

    override fun onPause() {
        super.onPause()
        ScreenManagerProvider.getScreenManager().removeScreenInfoListener(this)
    }

    private fun setupLayout(screenInfo: ScreenInfo) {
        if (!screenInfo.isDualMode()) {
            setupLayoutForSingleScreen()
        } else {
            setupLayoutForDualScreen()
        }
    }

    private fun setupLayoutForSingleScreen() {
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

    private fun setupLayoutForDualScreen() {
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
