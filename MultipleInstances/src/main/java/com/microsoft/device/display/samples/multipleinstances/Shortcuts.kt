/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.multipleinstances

import android.content.Context
import android.content.Intent
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.net.Uri

fun Context.createShortcuts() {
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