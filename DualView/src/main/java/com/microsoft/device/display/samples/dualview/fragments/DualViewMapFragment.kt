/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.dualview.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.microsoft.device.display.samples.dualview.R
import com.microsoft.device.display.samples.dualview.view.SelectedViewModel
import com.microsoft.device.display.samples.dualview.view.SelectedViewModel.Companion.NO_ITEM_SELECTED
import com.microsoft.device.dualscreen.ScreenInfo
import com.microsoft.device.dualscreen.ScreenInfoListener
import com.microsoft.device.dualscreen.ScreenManagerProvider
import kotlinx.android.synthetic.main.fragment_dual_view_map.*

/**
 * [Fragment] implementation that contains the fake map view
 */
class DualViewMapFragment : Fragment(), ScreenInfoListener {
    private val selectedViewModel: SelectedViewModel by activityViewModels()
    private var currentScreenInfo: ScreenInfo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        if (currentScreenInfo?.isDualMode() == false) {
            menu.clear()
            inflater.inflate(R.menu.menu_duak_view_map, menu)
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.dual_view_action_list -> {
                activity?.onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_dual_view_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeSelectedRestaurant()
    }

    /**
     * Observes the selected restaurant in order to setup the fake map view
     */
    private fun observeSelectedRestaurant() {
        selectedViewModel.selectedPosition.observe(
            viewLifecycleOwner,
            {
                setupMap(it)
            }
        )
    }

    /**
     * Setup for the fake map view.
     * Shows the image corresponding to the selected restaurant
     */
    private fun setupMap(selectedRestaurantPosition: Int) {
        val drawableResId = if (selectedRestaurantPosition != NO_ITEM_SELECTED) {
            val item = selectedViewModel.getItem(selectedRestaurantPosition)
            if (item != null && item.mapImageResourceId != 0) {
                item.mapImageResourceId
            } else {
                R.drawable.unselected_map
            }
        } else {
            R.drawable.unselected_map
        }
        map_view.setImageResource(drawableResId)
    }

    override fun onScreenInfoChanged(screenInfo: ScreenInfo) {
        currentScreenInfo = screenInfo
        requireActivity().invalidateOptionsMenu()
    }

    override fun onStart() {
        super.onStart()
        ScreenManagerProvider.getScreenManager().addScreenInfoListener(this)
    }

    override fun onResume() {
        super.onResume()
        ScreenManagerProvider.getScreenManager().removeScreenInfoListener(this)
    }
}
