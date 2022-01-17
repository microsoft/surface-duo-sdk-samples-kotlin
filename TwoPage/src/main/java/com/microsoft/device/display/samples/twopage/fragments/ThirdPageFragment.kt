/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.twopage.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import com.microsoft.device.display.samples.twopage.databinding.FragmentTwoPageThirdPageBinding

/**
 * Implementation for the third page
 */
class ThirdPageFragment : BasePageFragment() {
    private lateinit var binding: FragmentTwoPageThirdPageBinding
    override fun getScrollingContent(): ScrollView = binding.page3Content

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTwoPageThirdPageBinding.inflate(inflater, container, false)
        return binding.root
    }
}