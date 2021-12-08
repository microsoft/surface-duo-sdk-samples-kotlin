/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.duosamples

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.microsoft.device.display.samples.duosamples.databinding.FragmentDuoSamplesBinding
import com.microsoft.device.display.samples.duosamples.samples.Sample
import com.microsoft.device.display.samples.duosamples.samples.SampleBuilder
import com.microsoft.device.display.samples.duosamples.samples.SampleModel
import com.microsoft.device.display.samples.listdetail.extensions.isInPortrait
import com.microsoft.device.dualscreen.ScreenInfo
import com.microsoft.device.dualscreen.ScreenInfoListener
import com.microsoft.device.dualscreen.ScreenManagerProvider

/**
 * A [Fragment] that displays the list of samples.
 */
class DuoSamplesListFragment : Fragment(), ScreenInfoListener {

    private lateinit var viewModel: SampleViewModel
    private lateinit var binding: FragmentDuoSamplesBinding
    private var screenInfo: ScreenInfo? = null

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

    override fun onResume() {
        super.onResume()
        ScreenManagerProvider.getScreenManager().addScreenInfoListener(this)
    }

    override fun onPause() {
        super.onPause()
        ScreenManagerProvider.getScreenManager().removeScreenInfoListener(this)
    }

    override fun onScreenInfoChanged(screenInfo: ScreenInfo) {
        this.screenInfo = screenInfo
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
        screenInfo?.let { screenInfo ->
            if (!screenInfo.isDualMode() || screenInfo.isInPortrait) {
                val intent = Intent(requireActivity(), SampleBuilder.getSampleActivity(sample.type))
                startActivity(intent)
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