/*
 * Copyright (c) Microsoft Corporation. All rights reserved.Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.duosamples

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity

class DuoSamplesMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.duo_samples_activity_main)

        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        onBackPressed()
        return true
    }
}