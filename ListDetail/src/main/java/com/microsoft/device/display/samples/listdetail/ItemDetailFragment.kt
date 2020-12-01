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
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.microsoft.device.display.samples.listdetail.model.SelectionViewModel
import com.microsoft.device.dualscreen.core.ScreenHelper

class ItemDetailFragment : Fragment() {

    private lateinit var imageView: ImageView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(
            R.layout.fragment_item_detail,
            container,
            false
        )
        imageView = view.findViewById(R.id.imageView)

        if (!ScreenHelper.isDualMode(requireActivity())) {
            val toolbar = view.findViewById<Toolbar>(R.id.detail_toolbar)
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
            toolbar.setNavigationOnClickListener { closeFragment() }
        }

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val viewModel = ViewModelProvider(requireActivity()).get(SelectionViewModel::class.java)
        viewModel.getSelectedItemLiveData().observe(
            viewLifecycleOwner,
            Observer {
                it?.let { imageView.setImageResource(it) }
            }
        )
    }

    private fun closeFragment() {
        parentFragmentManager.beginTransaction()
            .replace(
                R.id.first_container_id,
                ItemsListFragment(),
                null
            ).addToBackStack(null)
            .commit()
    }
}
