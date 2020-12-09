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
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.microsoft.device.display.samples.contentcontext.model.Restaurant
import com.microsoft.device.display.samples.contentcontext.view.RestaurantAdapter
import com.microsoft.device.display.samples.contentcontext.view.SelectedViewModel
import com.microsoft.device.display.samples.contentcontext.view.SelectedViewModel.Companion.NO_ITEM_SELECTED
import com.microsoft.device.dualscreen.ScreenInfo
import com.microsoft.device.dualscreen.ScreenInfoListener
import com.microsoft.device.dualscreen.ScreenInfoProvider
import com.microsoft.device.dualscreen.ScreenManagerProvider

class ListFragment : Fragment(), ScreenInfoListener {
    private var adapterItems: RestaurantAdapter? = null
    private val selectedViewModel: SelectedViewModel by activityViewModels()
    private lateinit var listToolbar: Toolbar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layout = inflater.inflate(R.layout.fragment_items_list, container, false)

        activity?.let { activity ->
            val recyclerView = layout.findViewById<RecyclerView>(R.id.list_items)

            adapterItems =
                RestaurantAdapter(
                    activity,
                    selectedViewModel,
                    ::onItemClick
                )
            recyclerView.adapter = adapterItems
            recyclerView.setHasFixedSize(true)

            val divider = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            ResourcesCompat.getDrawable(resources, R.drawable.item_divider, null)?.let {
                divider.setDrawable(it)
            }
            recyclerView.addItemDecoration(divider)

            listToolbar = layout.findViewById(R.id.list_toolbar)
            listToolbar.inflateMenu(R.menu.menu_list)
            listToolbar.setOnMenuItemClickListener { onMenuItemSelected(it) }
        }

        return layout
    }

    override fun onScreenInfoChanged(screenInfo: ScreenInfo) {
        if (!screenInfo.isDualMode()) {
            adapterItems?.selectItem(NO_ITEM_SELECTED)
        }

        if (screenInfo.isDualMode()) {
            listToolbar.menu?.findItem(R.id.action_map)?.isVisible = false
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

    private fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.action_map -> {
                startDetailsFragment(NO_ITEM_SELECTED)
                true
            }
            else -> false
        }
    }

    private fun onItemClick(item: Restaurant) {
        activity?.let { activity ->
            if (ScreenInfoProvider.getScreenInfo(activity).isDualMode()) {
                adapterItems?.selectItem(selectedViewModel.getItemPosition(item))
                parentFragmentManager
                    .beginTransaction()
                    .replace(
                        R.id.second_container_id,
                        MapFragment(), null
                    )
                    .commit()
            } else {
                startDetailsFragment(selectedViewModel.getItemPosition(item))
            }
        }
    }

    private fun startDetailsFragment(pos: Int) {
        adapterItems?.selectItem(pos)
        parentFragmentManager.beginTransaction()
            .replace(
                R.id.first_container_id,
                MapFragment(), null
            ).addToBackStack(null)
            .commit()
    }
}
