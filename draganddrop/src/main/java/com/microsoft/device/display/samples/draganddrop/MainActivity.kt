/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.draganddrop

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.microsoft.device.display.samples.draganddrop.fragment.DragSourceFragment
import com.microsoft.device.display.samples.draganddrop.fragment.DropTargetFragment
import com.microsoft.device.dualscreen.layout.ScreenModeListener

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        reset_button.setOnClickListener { recreate() }

        (application as DragAndDropApp).surfaceDuoScreenManager.addScreenModeListener(
                object : ScreenModeListener {
                    override fun onSwitchToSingleScreenMode() {
                        supportFragmentManager.beginTransaction()
                                .replace(
                                    R.id.drag_source_container,
                                    DragSourceFragment.newInstance()
                                ).commit()
                        supportFragmentManager.beginTransaction()
                                .replace(
                                    R.id.drop_target_container,
                                    DropTargetFragment.newInstance()
                                ).commit()
                    }

                    override fun onSwitchToDualScreenMode() {
                        supportFragmentManager.beginTransaction()
                                .replace(
                                    R.id.dual_screen_start_container_id,
                                    DragSourceFragment.newInstance()
                                ).commit()
                        supportFragmentManager.beginTransaction()
                                .replace(
                                    R.id.dual_screen_end_container_id,
                                    DropTargetFragment.newInstance()
                                ).commit()
                    }
                }
        )
    }
}
