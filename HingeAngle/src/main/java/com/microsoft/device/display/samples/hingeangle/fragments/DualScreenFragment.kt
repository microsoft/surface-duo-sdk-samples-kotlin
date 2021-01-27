/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.hingeangle.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.microsoft.device.display.samples.hingeangle.R
import com.microsoft.device.display.samples.hingeangle.extensions.flip
import com.microsoft.device.display.samples.hingeangle.model.HingeAngleViewModel

/**
 * Fragment containing the flipped image.
 */
class DualScreenFragment : Fragment() {
    private lateinit var imageView: ImageView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.hinge_angle_fragment_dual_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindUI(view)
        observeImage()
    }

    /**
     * Binds all UI components.
     */
    private fun bindUI(view: View) {
        imageView = view.findViewById(R.id.imageView)
    }

    /**
     * Observes the current image and set the flipped image inside this screen.
     */
    private fun observeImage() {
        val viewModel = ViewModelProvider(requireActivity()).get(HingeAngleViewModel::class.java)
        viewModel.imageLiveData.observe(
            viewLifecycleOwner,
            {
                if (it != null) {
                    imageView.setImageBitmap(it.flip())
                } else {
                    imageView.setImageDrawable(null)
                }
            }
        )
    }
}
