/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.twopage.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.ScrollView
import androidx.fragment.app.Fragment

/**
 * Base [Fragment] for the content pages
 */
abstract class BasePageFragment : Fragment(), ScrollingContent {
    private var isScrollingEnabled = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnTouchListener(getScrollingContent())
    }

    @SuppressLint("ClickableViewAccessibility")
    protected fun setOnTouchListener(scrollView: ScrollView) {
        scrollView.setOnTouchListener { v, _ ->
            v.performClick()
            !isScrollingEnabled
        }
    }

    override fun enableScroll(enabled: Boolean) {
        isScrollingEnabled = enabled
    }

    /**
     * @return the [ScrollView] from the page
     */
    abstract fun getScrollingContent(): ScrollView
}