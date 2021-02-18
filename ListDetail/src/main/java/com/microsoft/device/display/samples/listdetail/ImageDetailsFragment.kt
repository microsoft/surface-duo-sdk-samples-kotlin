/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.listdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.microsoft.device.display.samples.listdetail.extensions.isInPortrait
import com.microsoft.device.display.samples.listdetail.model.SelectionViewModel
import com.microsoft.device.dualscreen.ScreenInfo
import com.microsoft.device.dualscreen.ScreenInfoListener
import com.microsoft.device.dualscreen.ScreenManagerProvider
import kotlinx.android.synthetic.main.list_details_fragment_image_details.*

/**
 * Contains selected image in full screen mode
 */
class ImageDetailsFragment : Fragment(), ScreenInfoListener {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.list_details_fragment_image_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeSelectedImage()
    }

    private fun observeSelectedImage() {
        val viewModel = ViewModelProvider(requireActivity()).get(SelectionViewModel::class.java)
        viewModel.selectedItem.observe(
            viewLifecycleOwner,
            {
                imageView.setImageResource(it)
            }
        )
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
        setupLayout(screenInfo)
    }

    private fun setupLayout(screenInfo: ScreenInfo) {
        val guidLinePercent = when {
            screenInfo.isDualMode() && screenInfo.isInPortrait -> 0.45f
            else -> 0.70f
        }

        guid_line.setGuidelinePercent(guidLinePercent)
    }
}
