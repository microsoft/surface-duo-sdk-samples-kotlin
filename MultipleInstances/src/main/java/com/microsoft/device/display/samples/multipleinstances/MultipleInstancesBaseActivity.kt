/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.multipleinstances

import android.content.Intent
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.net.Uri
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
        addShortcuts()
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

    private fun addShortcuts() {
        val mainActivityIntent = Intent(Intent.ACTION_MAIN, Uri.EMPTY, this, MultipleInstancesMainActivity::class.java)
        mainActivityIntent.addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP)

        val secondActivityIntent = Intent(Intent.ACTION_MAIN, Uri.EMPTY, this, MultipleInstancesSecondActivity::class.java)
        mainActivityIntent.addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP)

        getSystemService(ShortcutManager::class.java)?.let { shortcutManager ->
            val mainShortcut = ShortcutInfo.Builder(this, "id1")
                .setShortLabel(getString(R.string.multiple_instances_main_shortcut_label))
                .setLongLabel(getString(R.string.multiple_instances_main_shortcut_label))
                .setIntent(mainActivityIntent)
                .build()
            val secondShortcut = ShortcutInfo.Builder(this, "id2")
                .setShortLabel(getString(R.string.multiple_instances_second_shortcut_label))
                .setLongLabel(getString(R.string.multiple_instances_second_shortcut_label))
                .setIntent(secondActivityIntent)
                .build()
            shortcutManager.dynamicShortcuts = listOf(mainShortcut, secondShortcut)
        }
    }
}