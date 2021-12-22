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
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.window.layout.WindowInfoRepository
import androidx.window.layout.WindowInfoRepository.Companion.windowInfoRepository
import androidx.window.layout.WindowLayoutInfo
import com.microsoft.device.display.samples.dualview.R
import com.microsoft.device.display.samples.dualview.databinding.FragmentDualViewRestaurantsBinding
import com.microsoft.device.display.samples.dualview.model.Restaurant
import com.microsoft.device.display.samples.dualview.util.TAG
import com.microsoft.device.display.samples.dualview.view.RestaurantAdapter
import com.microsoft.device.display.samples.dualview.view.SelectedViewModel
import com.microsoft.device.display.samples.dualview.view.SelectedViewModel.Companion.NO_ITEM_SELECTED
import com.microsoft.device.dualscreen.utils.wm.isInDualMode

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * [Fragment] implementation that contains the restaurant list
 */
class DualViewRestaurantsFragment : Fragment() {
    private lateinit var binding: FragmentDualViewRestaurantsBinding
    private val selectedViewModel: SelectedViewModel by activityViewModels()

    private lateinit var windowInfoRepository: WindowInfoRepository
    private var windowLayoutInfo: WindowLayoutInfo? = null

    private var restaurantAdapter: RestaurantAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDualViewRestaurantsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        if (!windowLayoutInfo.isInDualMode()) {
            menu.clear()
            inflater.inflate(R.menu.menu_dual_view_list, menu)
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.dual_view_action_map -> {
                markSelectedRestaurant(NO_ITEM_SELECTED)
                showMapFragment(R.id.first_container_id)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRestaurantList()
    }

    /**
     * Setup for the restaurant [RecyclerView]
     */
    private fun setupRestaurantList() {
        restaurantAdapter = RestaurantAdapter(requireContext(), selectedViewModel, ::onItemClick)
        binding.restaurantsRecyclerView.adapter = restaurantAdapter
        binding.restaurantsRecyclerView.setHasFixedSize(true)
        val divider = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        ResourcesCompat.getDrawable(resources, R.drawable.item_divider, null)?.let {
            divider.setDrawable(it)
        }
        binding.restaurantsRecyclerView.addItemDecoration(divider)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        observeWindowLayoutInfo(context as AppCompatActivity)
    }

    private fun observeWindowLayoutInfo(activity: AppCompatActivity) {
        windowInfoRepository = activity.windowInfoRepository()
        lifecycleScope.launch(Dispatchers.Main) {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                windowInfoRepository.windowLayoutInfo.collect {
                    onWindowLayoutInfoChanged(it)
                }
            }
        }
    }

    private fun onWindowLayoutInfoChanged(windowLayoutInfo: WindowLayoutInfo) {
        this.windowLayoutInfo = windowLayoutInfo
        activity?.invalidateOptionsMenu()
        if (!windowLayoutInfo.isInDualMode()) {
            restaurantAdapter?.selectItem(NO_ITEM_SELECTED)
        }
    }

    /**
     * Called when a restaurant is selected
     */
    private fun onItemClick(item: Restaurant) {
        val mapContainerId = when {
            windowLayoutInfo.isInDualMode() -> R.id.second_container_id
            else -> R.id.first_container_id
        }
        markSelectedRestaurant(selectedViewModel.getItemPosition(item))
        showMapFragment(mapContainerId)
    }

    /**
     * Adds some visual changes for the selected restaurant
     */
    private fun markSelectedRestaurant(position: Int) {
        restaurantAdapter?.selectItem(position)
    }

    /**
     * Adds the map fragment to the given container
     *
     * @param containerId The view id used as the container for the [DualViewMapFragment]
     */
    private fun showMapFragment(@IdRes containerId: Int) {
        val fragment = DualViewMapFragment()
        val transaction = parentFragmentManager.beginTransaction()
            .replace(containerId, fragment, fragment.TAG)

        if (!windowLayoutInfo.isInDualMode()) {
            transaction.addToBackStack(null)
        }

        transaction.commit()
    }
}
