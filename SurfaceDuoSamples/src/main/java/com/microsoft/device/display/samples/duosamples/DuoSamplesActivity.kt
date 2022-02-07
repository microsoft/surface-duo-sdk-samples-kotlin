/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.duosamples

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.FoldableNavigation
import com.microsoft.device.display.samples.duosamples.databinding.ActivityDuoSamplesBinding
import com.microsoft.device.display.samples.duosamples.navigation.MainNavigator
import com.microsoft.device.display.samples.duosamples.navigation.MainNavigatorHost

class DuoSamplesActivity : AppCompatActivity(), MainNavigatorHost {
    private lateinit var binding: ActivityDuoSamplesBinding
    private var navigator: MainNavigator = MainNavigator()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDuoSamplesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back)
        supportActionBar?.title = getString(R.string.surface_duo_samples_app_title)
    }

    override fun onResume() {
        super.onResume()
        FoldableNavigation.findNavController(this, R.id.launch_nav_host_fragment).let {
            navigator.bind(it)
        }
    }

    override fun onPause() {
        super.onPause()
        navigator.unbind()
    }

    override fun getMainNavigator(): MainNavigator {
        return navigator
    }

    override fun onBackPressed() {
        finish()
    }
}