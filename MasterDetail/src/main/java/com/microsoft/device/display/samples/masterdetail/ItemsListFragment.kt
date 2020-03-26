/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.masterdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView

import androidx.fragment.app.Fragment

import com.microsoft.device.display.samples.masterdetail.model.DataProvider
import com.microsoft.device.display.samples.masterdetail.model.MovieMock
import com.microsoft.device.dualscreen.layout.ScreenHelper

import java.util.ArrayList

class ItemsListFragment : Fragment(), AdapterView.OnItemClickListener {
    private var arrayAdapter: ArrayAdapter<MovieMock>? = null
    private var listView: ListView? = null
    private lateinit var movieMocks: ArrayList<MovieMock>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movieMocks = DataProvider.movieMocks
        activity?.let {
            arrayAdapter = ArrayAdapter(
                it,
                android.R.layout.simple_list_item_activated_1,
                movieMocks
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_items_list, container, false)
        listView = view.findViewById(R.id.list_view)
        listView?.let {
            it.adapter = arrayAdapter
            it.choiceMode = ListView.CHOICE_MODE_SINGLE
            it.onItemClickListener = this
        }
        return view
    }

    private fun setSelectedItem(position: Int) {
        listView?.setItemChecked(position, true)
    }

    override fun onItemClick(adapterView: AdapterView<*>, item: View, position: Int, rowId: Long) {
        val movieMock = arrayAdapter?.getItem(position)
        setSelectedItem(position)
        movieMock?.let { movie ->
            activity?.let { activity ->
                if (ScreenHelper.isDualMode(activity)) {
                    parentFragmentManager.beginTransaction()
                            .replace(
                                    R.id.dual_screen_end_container_id,
                                    ItemDetailFragment.newInstance(movie), null
                            ).commit()
                } else {
                    startDetailsFragment(movie)
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
