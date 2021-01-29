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
import androidx.annotation.IdRes
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import com.microsoft.device.display.samples.dualview.R
import com.microsoft.device.display.samples.dualview.model.Restaurant
import com.microsoft.device.display.samples.dualview.util.TAG
import com.microsoft.device.display.samples.dualview.view.RestaurantAdapter
import com.microsoft.device.display.samples.dualview.view.SelectedViewModel
import com.microsoft.device.display.samples.dualview.view.SelectedViewModel.Companion.NO_ITEM_SELECTED
import com.microsoft.device.dualscreen.ScreenInfo
import com.microsoft.device.dualscreen.ScreenInfoListener
import com.microsoft.device.dualscreen.ScreenManagerProvider
import kotlinx.android.synthetic.main.fragment_restaurants.*

/**
 * [Fragment] implementation that contains the restaurant list
 */
class RestaurantsFragment : Fragment(), ScreenInfoListener {
    private var restaurantAdapter: RestaurantAdapter? = null
    private val selectedViewModel: SelectedViewModel by activityViewModels()
    private var currentScreenInfo: ScreenInfo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        if (currentScreenInfo?.isDualMode() == false) {
            menu.clear()
            inflater.inflate(R.menu.menu_list, menu)
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_map -> {
                markSelectedRestaurant(NO_ITEM_SELECTED)
                showMapFragment(R.id.first_container_id)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_restaurants, container, false)
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
        restaurants_recycler_view.adapter = restaurantAdapter
        restaurants_recycler_view.setHasFixedSize(true)
        val divider = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        ResourcesCompat.getDrawable(resources, R.drawable.item_divider, null)?.let {
            divider.setDrawable(it)
        }
        restaurants_recycler_view.addItemDecoration(divider)
    }

    override fun onScreenInfoChanged(screenInfo: ScreenInfo) {
        currentScreenInfo = screenInfo
        activity?.invalidateOptionsMenu()
        if (!screenInfo.isDualMode()) {
            restaurantAdapter?.selectItem(NO_ITEM_SELECTED)
        }
    }

    override fun onStart() {
        super.onStart()
        ScreenManagerProvider.getScreenManager().addScreenInfoListener(this)
    }

    override fun onResume() {
        super.onResume()
        ScreenManagerProvider.getScreenManager().removeScreenInfoListener(this)
    }

    /**
     * Called when a restaurant is selected
     */
    private fun onItemClick(item: Restaurant) {
        val mapContainerId = when {
            currentScreenInfo?.isDualMode() == true -> R.id.second_container_id
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
     * @param containerId The view id used as the container for the [MapFragment]
     */
    private fun showMapFragment(@IdRes containerId: Int) {
        val fragment = MapFragment()
        parentFragmentManager.beginTransaction()
            .replace(containerId, fragment, fragment.TAG)
            .addToBackStack(null)
            .commit()
    }
}
