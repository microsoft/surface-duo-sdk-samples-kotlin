/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.contentcontext

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.microsoft.device.display.samples.contentcontext.model.DataProvider
import com.microsoft.device.display.samples.contentcontext.model.Restaurant
import com.microsoft.device.display.samples.contentcontext.view.RestaurantAdapter
import com.microsoft.device.display.samples.contentcontext.view.SelectedViewModel
import com.microsoft.device.dualscreen.layout.ScreenHelper

class ListFragment : Fragment() {
    private var adapterItems: RestaurantAdapter? = null
    private val selectedViewModel: SelectedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layout = inflater.inflate(R.layout.fragment_items_list, container, false)

        val recyclerView = layout.findViewById<RecyclerView>(R.id.list_items)

        activity?.let {
            if (!ScreenHelper.isDualMode(it)) {
                selectedViewModel.selectedPosition.value = -1
            }

            adapterItems =
                RestaurantAdapter(
                    it,
                    DataProvider.restaurants,
                    selectedViewModel.selectedPosition.value ?: -1,
                    ::onItemClick
                )
            recyclerView.adapter = adapterItems

            val divider = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            divider.setDrawable(resources.getDrawable(R.drawable.item_divider, null))
            recyclerView.addItemDecoration(divider)
            addSelectionObserver()
        }

        handleSpannedModeSelection()

        val listToolbar = layout.findViewById<Toolbar>(R.id.list_toolbar)
        listToolbar.inflateMenu(R.menu.menu_list)
        listToolbar.setOnMenuItemClickListener { onMenuItemSelected(it) }

        activity?.takeIf { ScreenHelper.isDualMode(it) }?.let {
            listToolbar.menu?.findItem(R.id.action_map)?.isVisible = false
        }

        return layout
    }

    private fun addSelectionObserver() {
        selectedViewModel.selectedPosition.observe(
            requireActivity(),
            Observer {
                if (it != -1) {
                    adapterItems?.selectItem(it)
                }
            }
        )
    }

    private fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.action_map -> {
                startDetailsFragment(null)
                true
            }
            else -> false
        }
    }

    private fun onItemClick(item: Restaurant) {
        activity?.let { activity ->
            if (ScreenHelper.isDualMode(activity)) {
                if (selectedViewModel.selectedPosition.value != adapterItems?.getItemPosition(item)) {
                    selectedViewModel.selectedPosition.value = adapterItems?.getItemPosition(item)
                    parentFragmentManager
                        .beginTransaction()
                        .replace(
                            R.id.dual_screen_end_container_id,
                            MapFragment.newInstance(item), null
                        )
                        .commit()
                }
            } else {
                startDetailsFragment(item)
            }
        }
    }

    private fun startDetailsFragment(item: Restaurant?) {
        parentFragmentManager.beginTransaction()
            .replace(
                R.id.single_screen_container_id,
                MapFragment.newInstance(item), null
            ).addToBackStack(null)
            .commit()
    }

    private fun handleSpannedModeSelection() {
        activity?.takeIf { ScreenHelper.isDualMode(it) }?.let { _ ->
            var selectedItem: Restaurant? = null
            selectedViewModel.selectedPosition.value?.let {
                selectedItem = adapterItems?.getItem(it)
            }
            parentFragmentManager
                .beginTransaction()
                .replace(
                    R.id.dual_screen_end_container_id,
                    MapFragment.newInstance(selectedItem), null
                )
                .commit()
        }
    }
}
