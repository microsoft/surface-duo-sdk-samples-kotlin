/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.complementarycontext

import android.os.Bundle
import android.view.Surface
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.microsoft.device.display.samples.complementarycontext.adapters.NotesAdapter
import com.microsoft.device.display.samples.complementarycontext.adapters.SlidesAdapter
import com.microsoft.device.display.samples.complementarycontext.model.DataProvider
import com.microsoft.device.dualscreen.layout.ScreenHelper

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (!ScreenHelper.isDualMode(this)) {
            val slidesPager = findViewById<ViewPager2>(R.id.slides_pager)
            setupViewPager(slidesPager)
        } else {
            val slidesPager = findViewById<ViewPager2>(R.id.slides_pager)
            setupViewPager(slidesPager)

            // Handle DualScreenEndLayout Toolbar visibility
            val toolbar = findViewById<Toolbar>(R.id.dual_screen_end_toolbar)
            when (ScreenHelper.getCurrentRotation(this@MainActivity)) {
                Surface.ROTATION_0, Surface.ROTATION_180 -> toolbar.visibility = View.VISIBLE
                Surface.ROTATION_90, Surface.ROTATION_270 -> toolbar.visibility = View.GONE
            }

            // Dual End Screen
            val notesRecyclerView = findViewById<RecyclerView>(R.id.notes_recycler_view)
            notesRecyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
            val notesAdapter = NotesAdapter()
            notesAdapter.submitList(DataProvider.slides)
            notesRecyclerView.adapter = notesAdapter
            notesAdapter.setSlidesPager(slidesPager)

            slidesPager.registerOnPageChangeCallback(
                object : ViewPager2.OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        super.onPageSelected(position)
                        notesRecyclerView.scrollToPosition(position)

                        NotesAdapter.oldSelectionPosition = NotesAdapter.selectionPosition
                        NotesAdapter.selectionPosition = position
                        notesAdapter.notifyItemChanged(NotesAdapter.oldSelectionPosition)
                        notesAdapter.notifyItemChanged(position)
                    }
                })
        }
    }

    fun setupViewPager(slidesPager: ViewPager2) {
        // Setup Single screen / Dual Start Screen
        val slidesAdapter = SlidesAdapter()
        slidesAdapter.submitList(DataProvider.slides)
        slidesPager.adapter = slidesAdapter
    }
}
