/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.qualifiers

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.microsoft.device.display.samples.qualifiers.databinding.ActivityQualifiersBinding

class QualifiersActivity : AppCompatActivity() {
    private lateinit var binding: ActivityQualifiersBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQualifiersBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
}
