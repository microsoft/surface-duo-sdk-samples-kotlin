/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.duosamples

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.microsoft.device.display.samples.duosamples.databinding.ActivityDuoSamplesBinding

class DuoSamplesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDuoSamplesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDuoSamplesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back)
        supportActionBar?.title = getString(R.string.surface_duo_samples_app_title)
    }
}