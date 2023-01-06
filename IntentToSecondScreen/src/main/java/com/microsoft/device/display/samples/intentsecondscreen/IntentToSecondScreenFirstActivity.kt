/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.intentsecondscreen

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.window.layout.WindowInfoTracker
import com.microsoft.device.display.samples.intentsecondscreen.databinding.ActivityFirstIntentToSecondScreenBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * {@link Intent.FLAG_ACTIVITY_MULTIPLE_TASK} along with android:launchMode="singleInstance"
 * is required to launch a second activity on a second display while still keeping the first
 * activity on the first display(not pausing/stopping it).
 *
 * When using these flags, the activities will have separate tasks. This means that
 * FirstActivity is launched in screen 1 (left) and second activity is launched in
 * screen 2 (right). Then, if you launch other activities from second activity you should
 * not use MULTIPLE_TASK anymore if you want the other activities to be on second
 * screen(the same task as second activity is).
 *
 * You should use {@link Intent.FLAG_ACTIVITY_MULTIPLE_TASK} along with
 * android:launchMode="singleInstance" or for multi-screen purpose (to have 2 separate tasks.
 * one for first screen and one for second screen).
 *
 * You should also use {@link Intent.FLAG_ACTIVITY_SINGLE_TOP} with #MULTIPLE_TASK and
 * "singleInstance" as below if you want to make sure that you don't have multiple instances
 * of the same activity on different tasks open.
 *
 */
class IntentToSecondScreenFirstActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFirstIntentToSecondScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFirstIntentToSecondScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        registerWindowInfoFlow()
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

    private fun registerWindowInfoFlow() {
        lifecycleScope.launch(Dispatchers.Main) {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                WindowInfoTracker.getOrCreate(this@IntentToSecondScreenFirstActivity)
                    .windowLayoutInfo(this@IntentToSecondScreenFirstActivity)
                    .collect {
                        setListeners()
                    }
            }
        }
    }


    private fun setListeners() {
        binding.root.findViewById<Button>(R.id.open_second_screen)
            .setOnClickListener { openSecondScreen() }
        binding.root.findViewById<Button>(R.id.open_browser).setOnClickListener {
            openBrowserApp(resources.getString(R.string.intent_app_web_link))
        }
        binding.root.findViewById<Button>(R.id.open_map).setOnClickListener {
            openBrowserApp(resources.getString(R.string.intent_app_map_link))
        }
    }

    private fun openSecondScreen() {
        openActivity(Intent(this, IntentToSecondScreenSecondActivity::class.java))
    }

    private fun openBrowserApp(url: String) {
        openActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }

    private fun openActivity(intent: Intent) {
        intent.apply {
            // Intent.FLAG_ACTIVITY_LAUNCH_ADJACENT is required to launch a second activity
            // on a second display while still keeping the first activity on the first display
            // (not pausing/stopping it)
            addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        }.also {
            startActivity(it)
        }
    }
}
