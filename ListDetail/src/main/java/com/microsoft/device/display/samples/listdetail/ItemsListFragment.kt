/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.listdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.microsoft.device.display.samples.listdetail.model.DataProvider
import com.microsoft.device.display.samples.listdetail.model.ImageAdapter
import com.microsoft.device.display.samples.listdetail.model.SelectionViewModel
import com.microsoft.device.dualscreen.core.ScreenHelper

class ItemsListFragment : Fragment() {
    private var imageAdapter: ImageAdapter? = null
    private lateinit var images: ArrayList<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        images = DataProvider.imageIDs
        activity?.let {
            imageAdapter = ImageAdapter(
                it,
                images,
                ::onItemClick
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_items_list, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.image_list)

        val layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        layoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = imageAdapter

        return view
    }

    private fun onItemClick(item: Int) {
        val viewModel = ViewModelProvider(requireActivity()).get(SelectionViewModel::class.java)
        viewModel.setSelectedItemLiveData(item)

        if (ScreenHelper.isDualMode(requireActivity()) && !MainActivity.isPortrait(requireActivity())) {
            parentFragmentManager.beginTransaction()
                .replace(
                    R.id.second_container_id,
                    ItemDetailFragment(),
                    null
                ).commit()
        } else {
            parentFragmentManager.beginTransaction()
                .replace(
                    R.id.first_container_id,
                    ItemDetailFragment(),
                    null
                ).addToBackStack(null)
                .commit()
        }
    }
}