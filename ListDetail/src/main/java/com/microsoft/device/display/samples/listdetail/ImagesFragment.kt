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
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.microsoft.device.display.samples.listdetail.model.DataProvider
import com.microsoft.device.display.samples.listdetail.model.ImageAdapter
import com.microsoft.device.display.samples.listdetail.model.SelectionViewModel
import kotlinx.android.synthetic.main.list_details_fragment_images.*

/**
 * Contains the image gallery
 */
class ImagesFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.list_details_fragment_images, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupImages()
    }

    private fun setupImages() {
        val layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        layoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
        imagesRecyclerView.layoutManager = layoutManager
        imagesRecyclerView.adapter = ImageAdapter(requireContext(), DataProvider.imageIDs, ::onClickImage)
    }

    private fun onClickImage(imageResId: Int) {
        val viewModel = ViewModelProvider(requireActivity()).get(SelectionViewModel::class.java)
        viewModel.selectedItem.value = imageResId
    }
}