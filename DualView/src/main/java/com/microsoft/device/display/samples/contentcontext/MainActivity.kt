/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.contentcontext

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.microsoft.device.dualscreen.ScreenInfo
import com.microsoft.device.dualscreen.ScreenInfoListener
import com.microsoft.device.dualscreen.ScreenManagerProvider

class MainActivity : FragmentActivity(), ScreenInfoListener {
    companion object {
        private const val FRAGMENT_DUAL_START = "FragmentDualStart"
        private const val FRAGMENT_DUAL_END = "FragmentDualEnd"
        private const val FRAGMENT_SINGLE_SCREEN = "FragmentSingleScreen"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        ScreenManagerProvider.getScreenManager().addScreenInfoListener(this)
    }

    override fun onPause() {
        super.onPause()
        ScreenManagerProvider.getScreenManager().removeScreenInfoListener(this)
    }

    override fun onScreenInfoChanged(screenInfo: ScreenInfo) {
        if (screenInfo.isDualMode()) {
            setupDualScreenFragments()
        } else {
            setupSingleScreenFragments()
        }
    }

    private fun setupSingleScreenFragments() {
        if (supportFragmentManager.findFragmentByTag(FRAGMENT_SINGLE_SCREEN) == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.first_container_id, ListFragment(), FRAGMENT_SINGLE_SCREEN)
                .commit()
        }
    }
    private fun setupDualScreenFragments() {
        if (supportFragmentManager.findFragmentByTag(FRAGMENT_DUAL_START) == null &&
            supportFragmentManager.findFragmentByTag(FRAGMENT_DUAL_END) == null
        ) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.first_container_id, ListFragment(), FRAGMENT_DUAL_START)
                .commit()

            supportFragmentManager.beginTransaction()
                .replace(R.id.second_container_id, MapFragment(), FRAGMENT_DUAL_END)
                .commit()
        }
    }
}
