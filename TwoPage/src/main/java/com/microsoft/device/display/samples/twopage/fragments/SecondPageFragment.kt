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
import com.microsoft.device.display.samples.twopage.R
import kotlinx.android.synthetic.main.fragment_second_page.*

/**
 * Implementation for the second page
 */
class SecondPageFragment : BasePageFragment() {
    override fun getScrollingContent(): ScrollView = page2_content

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_second_page, container, false)
    }
}