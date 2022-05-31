/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.listdetail

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.window.layout.WindowInfoTracker
import androidx.window.layout.WindowLayoutInfo
import com.microsoft.device.display.samples.listdetail.databinding.ListDetailsFragmentImageDetailsBinding
import com.microsoft.device.display.samples.listdetail.model.SelectionViewModel
import com.microsoft.device.dualscreen.utils.wm.isFoldingFeatureVertical
import com.microsoft.device.dualscreen.utils.wm.isInDualMode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * Contains selected image in full screen mode
 */
class ImageDetailsFragment : Fragment() {

    private lateinit var binding: ListDetailsFragmentImageDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ListDetailsFragmentImageDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeSelectedImage()
    }

    private fun observeSelectedImage() {
        val viewModel = ViewModelProvider(requireActivity()).get(SelectionViewModel::class.java)
        viewModel.selectedItem.observe(viewLifecycleOwner) {
            binding.imageView.setImageResource(it)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        registerWindowInfoFlow()
    }

    private fun registerWindowInfoFlow() {
        lifecycleScope.launch(Dispatchers.Main) {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                WindowInfoTracker.getOrCreate(requireContext())
                    .windowLayoutInfo(requireActivity())
                    .collect { windowLayoutInfo ->
                        setupLayout(windowLayoutInfo)
                    }
            }
        }
    }

    private fun setupLayout(windowLayoutInfo: WindowLayoutInfo) {
        val guidLinePercent = when {
            windowLayoutInfo.isInDualMode() && !windowLayoutInfo.isFoldingFeatureVertical() -> 0.45f
            else -> 0.70f
        }
        binding.guidLine.setGuidelinePercent(guidLinePercent)
    }
}
