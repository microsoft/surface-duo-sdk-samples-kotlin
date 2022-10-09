/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.dualview.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.window.layout.WindowInfoTracker
import androidx.window.layout.WindowLayoutInfo
import com.microsoft.device.display.samples.dualview.R
import com.microsoft.device.display.samples.dualview.databinding.FragmentDualViewMapBinding
import com.microsoft.device.display.samples.dualview.view.MapSimulator
import com.microsoft.device.display.samples.dualview.view.SelectedViewModel
import com.microsoft.device.dualscreen.utils.wm.isInDualMode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * [Fragment] implementation that contains the fake map view
 */
class DualViewMapFragment : Fragment() {
    private lateinit var binding: FragmentDualViewMapBinding
    private val selectedViewModel: SelectedViewModel by activityViewModels()
    private var windowLayoutInfo: WindowLayoutInfo? = null
    private lateinit var mapSimulator: MapSimulator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        mapSimulator = MapSimulator(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDualViewMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        if (!windowLayoutInfo.isInDualMode()) {
            menu.clear()
            inflater.inflate(R.menu.menu_dual_view_map, menu)
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeSelectedRestaurant()
    }

    /**
     * Observes the selected restaurant in order to setup the fake map view
     */
    private fun observeSelectedRestaurant() {
        selectedViewModel.selectedPosition.observe(viewLifecycleOwner) {
            setupMap(it)
        }
    }

    /**
     * Setup for the fake map view.
     * Shows the image corresponding to the selected restaurant
     */
    private fun setupMap(selectedRestaurantPosition: Int) {
        binding.mapView.setImageBitmap(
            mapSimulator.generateMap(
                selectedViewModel.listItems.map { item -> item.coordinates },
                selectedRestaurantPosition
            )
        )
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        registerWindowInfoFlow()
    }

    private fun registerWindowInfoFlow() {
        lifecycleScope.launch(Dispatchers.Main) {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                WindowInfoTracker.getOrCreate(requireContext())
                    .windowLayoutInfo(requireActivity())
                    .collect { windowLayoutInfo ->
                        onWindowLayoutInfoChanged(windowLayoutInfo)
                    }
            }
        }
    }

    private fun onWindowLayoutInfoChanged(windowLayoutInfo: WindowLayoutInfo) {
        this.windowLayoutInfo = windowLayoutInfo
        requireActivity().invalidateOptionsMenu()
    }
}
