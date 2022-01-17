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
import com.microsoft.device.display.samples.twopage.databinding.FragmentTwoPageFourthPageBinding

/**
 * Implementation for the fourth page
 */
class FourthPageFragment : BasePageFragment() {
    private lateinit var binding: FragmentTwoPageFourthPageBinding
    override fun getScrollingContent(): ScrollView = binding.page4Content

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTwoPageFourthPageBinding.inflate(inflater, container, false)
        return binding.root
    }
}