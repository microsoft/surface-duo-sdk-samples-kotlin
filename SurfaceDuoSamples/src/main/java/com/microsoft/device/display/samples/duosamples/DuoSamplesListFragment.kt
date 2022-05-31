/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.duosamples

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import androidx.window.layout.WindowInfoTracker
import androidx.window.layout.WindowLayoutInfo
import com.microsoft.device.display.samples.duosamples.databinding.FragmentDuoSamplesBinding
import com.microsoft.device.display.samples.duosamples.navigation.getMainNavigator
import com.microsoft.device.display.samples.duosamples.samples.Sample
import com.microsoft.device.display.samples.duosamples.samples.SampleBuilder
import com.microsoft.device.display.samples.duosamples.samples.SampleModel
import com.microsoft.device.dualscreen.utils.wm.isFoldingFeatureVertical
import com.microsoft.device.dualscreen.utils.wm.isInDualMode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * A [Fragment] that displays the list of samples.
 */
class DuoSamplesListFragment : Fragment() {

    private lateinit var viewModel: SampleViewModel
    private lateinit var binding: FragmentDuoSamplesBinding

    private var windowLayoutInfo: WindowLayoutInfo? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDuoSamplesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(SampleViewModel::class.java)
        prepareRecyclerView()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        registerWindowInfoFlow()
    }

    private fun getLaunchNavigator() = requireActivity().getMainNavigator()

    private fun registerWindowInfoFlow() {
        lifecycleScope.launch(Dispatchers.Main) {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                WindowInfoTracker.getOrCreate(requireContext())
                    .windowLayoutInfo(requireActivity())
                    .collect { windowLayoutInfo ->
                        onWindowLayoutInfoChanged(windowLayoutInfo)
                    }
            }
        }
    }

    private fun onWindowLayoutInfoChanged(windowLayoutInfo: WindowLayoutInfo) {
        this.windowLayoutInfo = windowLayoutInfo
        with(getLaunchNavigator()) {
            if (windowLayoutInfo.isInDualMode()) {
                navigateToSampleDescription()
            } else {
                navigateUp()
            }
        }
    }

    private fun prepareRecyclerView() {
        val samples = prepareSamples()
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.appRecycler.layoutManager = layoutManager
        binding.appRecycler.adapter =
            SamplesAdapter(requireContext(), samples, ::onClickSample)
        binding.appRecycler.addItemDecoration(VerticalSpaceItemDecoration(7))
        if (viewModel.selectedSample.value == null) {
            samples[0].isSelected = true
            viewModel.selectedSample.value = samples[0]
        }
    }

    private fun prepareSamples() = Sample.values().map { sample ->
        SampleModel(
            sample,
            SampleBuilder.getTitle(sample, requireContext()),
            SampleBuilder.getThumbnail(sample),
            SampleBuilder.getSimpleDescription(sample, requireContext()),
            SampleBuilder.getDetailedImage(sample),
            SampleBuilder.getDescription(sample, requireContext()),
            SampleBuilder.getGithubUrl(sample),
            false
        )
    }

    private fun onClickSample(sample: SampleModel) {
        viewModel.selectedSample.value = sample
        windowLayoutInfo?.let { windowLayoutInfo ->
            if (!windowLayoutInfo.isInDualMode() || !windowLayoutInfo.isFoldingFeatureVertical()) {
                getLaunchNavigator().navigateFromListToSample(sample.type)
            }
        }
    }
}

class VerticalSpaceItemDecoration(private val verticalSpaceHeight: Int) : ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.bottom = verticalSpaceHeight
    }
}