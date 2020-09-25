/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.contentcontext

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.microsoft.device.display.samples.contentcontext.model.DataProvider
import com.microsoft.device.display.samples.contentcontext.model.MapPoint
import com.microsoft.device.dualscreen.layout.ScreenHelper

class MapPointListFragment : Fragment() {
    private var adapterItems: ArrayAdapter<MapPoint>? = null
    private lateinit var lvItems: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mapPoints = DataProvider.mapPoints
        activity?.let {
            adapterItems = ArrayAdapter(
                it,
                android.R.layout.simple_list_item_activated_1,
                mapPoints
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_items_list, container, false)
        lvItems = view.findViewById(R.id.lvItems)
        lvItems.adapter = adapterItems
        lvItems.choiceMode = ListView.CHOICE_MODE_SINGLE

        setOnClickListenerForListView(lvItems)
        handleSpannedModeSelection()
        return view
    }

    private fun setOnClickListenerForListView(lvItems: ListView) {
        // Handle OnListItemClick depending on screen mode
        lvItems.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            lvItems.setItemChecked(position, true)
            val mapPoint = adapterItems?.getItem(position)
            activity?.let { activity ->
                if (ScreenHelper.isDualMode(activity)) {
                    mapPoint?.let {
                        parentFragmentManager
                            .beginTransaction()
                            .replace(
                                R.id.dual_screen_end_container_id,
                                MapFragment.newInstance(it), null
                            )
                            .commit()
                    }
                } else {
                    startDetailsFragment(mapPoint)
                }
            }
        }
    }

    private fun startDetailsFragment(mapPoint: MapPoint?) {
        mapPoint?.let {
            parentFragmentManager.beginTransaction()
                .replace(
                    R.id.single_screen_container_id,
                    MapFragment.newInstance(mapPoint), null
                ).addToBackStack(null)
                .commit()
        }
    }

    private fun handleSpannedModeSelection() {
        activity?.let {
            if (ScreenHelper.isDualMode(it)) {
                val position = 0
                lvItems.setItemChecked(position, true)

                val mapPoint = adapterItems?.getItem(position)
                mapPoint?.let {
                    parentFragmentManager
                        .beginTransaction()
                        .replace(
                            R.id.dual_screen_end_container_id,
                            MapFragment.newInstance(mapPoint), null
                        )
                        .commit()
                }
            }
        }
    }
}
