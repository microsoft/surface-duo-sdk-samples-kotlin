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
import kotlinx.android.synthetic.main.two_page_fragment_third_page.*

/**
 * Implementation for the third page
 */
class ThirdPageFragment : BasePageFragment() {
    override fun getScrollingContent(): ScrollView = page3_content

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.two_page_fragment_third_page, container, false)
    }
}