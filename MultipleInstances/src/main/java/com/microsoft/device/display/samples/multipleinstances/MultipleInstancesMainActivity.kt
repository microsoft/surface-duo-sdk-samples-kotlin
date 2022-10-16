/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.multipleinstances

import android.os.Bundle

/**
 * Implementation for [BaseActivity]
 */
class MultipleInstancesMainActivity : MultipleInstancesBaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.title = getString(R.string.multiple_instances_main_shortcut_label)
    }

    override fun getFirstScreenText(): String = getString(R.string.multiple_instances_main_activity_text)

    override fun getSecondScreenText(): String = getString(R.string.multiple_instances_second_activity_text)
}
