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
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.microsoft.device.display.samples.listdetail.model.DataProvider
import com.microsoft.device.display.samples.listdetail.model.ImageAdapter
import com.microsoft.device.display.samples.listdetail.model.MovieMock
import com.microsoft.device.display.samples.listdetail.model.SelectionViewModel
import com.microsoft.device.dualscreen.layout.ScreenHelper

class ItemsListFragment : Fragment(), AdapterView.OnItemClickListener {
    private var imageAdapter: ImageAdapter? = null
    private val selectionViewModel: SelectionViewModel by activityViewModels()
    private var listView: ListView? = null
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
//        activity?.let { activity ->
//            if (ScreenHelper.isDualMode(activity)) {
//                if (selectionViewModel.selectedPosition.value != imageAdapter?.getItemPosition(item)) {
//                    selectionViewModel.selectedPosition.value = imageAdapter?.getItemPosition(item)
//                    parentFragmentManager
//                        .beginTransaction()
//                        .replace(
//                            R.id.dual_screen_end_container_id,
//                            MapFragment.newInstance(item), null
//                        )
//                        .commit()
//                }
//            } else {
//                startDetailsFragment(item)
//            }
//        }
    }

    private fun setSelectedItem(position: Int) {
        listView?.setItemChecked(position, true)
    }

    override fun onItemClick(adapterView: AdapterView<*>, item: View, position: Int, rowId: Long) {
        val image = imageAdapter?.getItem(position)
        setSelectedItem(position)
        image?.let { movie ->
            activity?.let { activity ->
                if (ScreenHelper.isDualMode(activity)) {
                    parentFragmentManager.beginTransaction()
//                        .replace(
//                            R.id.dual_screen_end_container_id,
//                            ItemDetailFragment.newInstance(movie), null
//                        ).commit()
//                } else {
//                    startDetailsFragment(movie)
                }
            }
        }
    }

    private fun startDetailsFragment(movieMock: MovieMock) {
        parentFragmentManager.beginTransaction()
            .replace(
                R.id.single_screen_container_id,
                ItemDetailFragment.newInstance(movieMock), null
            ).addToBackStack(null)
            .commit()
    }
}