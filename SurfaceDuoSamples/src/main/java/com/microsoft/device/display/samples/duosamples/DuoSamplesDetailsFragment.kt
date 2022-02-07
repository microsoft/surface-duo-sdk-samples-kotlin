/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.duosamples

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.microsoft.device.display.samples.duosamples.databinding.FragmentDetailSamplesBinding
import com.microsoft.device.display.samples.duosamples.navigation.getMainNavigator
import com.microsoft.device.display.samples.duosamples.samples.SampleModel

/**
 * A [Fragment] that displays the details of the selected sample.
 */
class DuoSamplesDetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailSamplesBinding
    private lateinit var viewModel: SampleViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailSamplesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(SampleViewModel::class.java)
        viewModel.selectedSample.observe(
            viewLifecycleOwner,
            { sample ->
                onSampleSelected(sample)
            }
        )
        binding.githubSwitch.setOnCheckedChangeListener { _, isChecked ->
            onViewCodeChanged(isChecked)
        }
        binding.btnTryIt.setOnClickListener {
            viewModel.selectedSample.value?.let { sample ->
                getLaunchNavigator().navigateFromDetailsToSample(sample.type)
            }
        }
    }

    private fun getLaunchNavigator() = requireActivity().getMainNavigator()

    private fun onSampleSelected(sample: SampleModel?) {
        if (sample != null) {
            binding.parentContainer.visibility = View.VISIBLE

            binding.appName.text = sample.appName
            binding.detailsImage.setImageResource(sample.detailsImage)
            binding.appInfo.text = sample.detailsDescription
            binding.githubSwitch.isChecked = false
            binding.webView.loadUrl(sample.gitHubUrl)
        } else {
            binding.parentContainer.visibility = View.GONE
        }
    }

    private fun onViewCodeChanged(isChecked: Boolean) {
        if (isChecked) {
            binding.githubSwitch.setText(R.string.turn_off_code)
            binding.webView.visibility = View.VISIBLE
        } else {
            binding.githubSwitch.setText(R.string.view_the_code)
            binding.webView.visibility = View.GONE
        }
    }
}
