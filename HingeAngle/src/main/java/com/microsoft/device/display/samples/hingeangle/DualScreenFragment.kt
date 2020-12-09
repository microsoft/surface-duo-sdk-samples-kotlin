/*
 * Copyright (c) Microsoft Corporation. All rights reserved.Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.hingeangle

import android.graphics.Bitmap
import android.graphics.Matrix
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.microsoft.device.display.samples.hingeangle.model.ViewModel

class DualScreenFragment : Fragment() {

    private lateinit var imageView: ImageView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(
            R.layout.fragment_dual_screen,
            container,
            false
        )
        imageView = view.findViewById<ImageView>(R.id.imageView)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val viewModel = ViewModelProvider(requireActivity()).get(ViewModel::class.java)
        viewModel.getImageLiveData().observe(
            viewLifecycleOwner,
            Observer {
                if (it != null) {
                    imageView.setImageBitmap(flip(it))
                } else {
                    imageView.setImageDrawable(null)
                }
            }
        )
    }

    private fun flip(origin: Bitmap): Bitmap {
        val leftCopyBitmap = origin.copy(origin.config, true)
        val matrix = Matrix()
        matrix.preScale(-1.0f, 1.0f)
        return Bitmap.createBitmap(leftCopyBitmap, 0, 0, leftCopyBitmap.width, leftCopyBitmap.height, matrix, true)
    }
}