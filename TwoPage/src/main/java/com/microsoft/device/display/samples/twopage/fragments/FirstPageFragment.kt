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
import com.microsoft.device.display.samples.twopage.databinding.FragmentTwoPageFirstPageBinding

/**
 * Implementation for the first page
 */
class FirstPageFragment : BasePageFragment() {
    private lateinit var binding: FragmentTwoPageFirstPageBinding
    override fun getScrollingContent(): ScrollView = binding.page1Content

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTwoPageFirstPageBinding.inflate(inflater, container, false)
        return binding.root
    }
}