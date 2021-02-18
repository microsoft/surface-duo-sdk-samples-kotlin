/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.multipleinstances

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.microsoft.device.dualscreen.ScreenInfo
import com.microsoft.device.dualscreen.ScreenInfoListener
import com.microsoft.device.dualscreen.ScreenManagerProvider
import kotlinx.android.synthetic.main.multiple_instances_layout_first_screen.*
import kotlinx.android.synthetic.main.multiple_instances_layout_second_screen.*

/**
 * Base Activity that shows one TextView for single screen mode and two TextView's for dual screen mode.
 */
abstract class MultipleInstancesBaseActivity : AppCompatActivity(), ScreenInfoListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_multiple_instances)
        setupToolbar()
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

    override fun onResume() {
        super.onResume()
        ScreenManagerProvider.getScreenManager().addScreenInfoListener(this)
    }

    override fun onPause() {
        super.onPause()
        ScreenManagerProvider.getScreenManager().removeScreenInfoListener(this)
    }

    override fun onScreenInfoChanged(screenInfo: ScreenInfo) {
        first_screen_text.text = getFirstScreenText()

        if (screenInfo.isDualMode()) {
            second_screen_text.text = getSecondScreenText()
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