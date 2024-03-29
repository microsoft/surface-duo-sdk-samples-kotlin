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
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat.getColor
import androidx.core.view.drawToBitmap
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.microsoft.device.display.samples.hingeangle.R
import com.microsoft.device.display.samples.hingeangle.databinding.HingeAngleFragmentSingleScreenBinding
import com.microsoft.device.display.samples.hingeangle.model.DEFAULT_HINGE_ANGLE
import com.microsoft.device.display.samples.hingeangle.model.HingeAngleLiveData
import com.microsoft.device.display.samples.hingeangle.model.HingeAngleViewModel
import com.microsoft.device.display.samples.hingeangle.model.PaintColors
import com.microsoft.device.display.samples.hingeangle.model.UNAVAILABLE_HINGE
import com.microsoft.device.display.samples.hingeangle.model.color
import com.microsoft.device.display.samples.hingeangle.views.PenDrawingView

/**
 * Fragment containing the drawing surface and the color selector component.
 */
class SingleScreenFragment : Fragment() {
    private lateinit var binding: HingeAngleFragmentSingleScreenBinding
    private lateinit var viewModel: HingeAngleViewModel
    private lateinit var drawingView: PenDrawingView
    private lateinit var hingeAngleValueView: TextView
    private var currentAngle: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = HingeAngleFragmentSingleScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        bindUI(view)
        restoreDrawingSurfaceState()
        observeHingeAngle()
    }

    /**
     * Setup for [HingeAngleViewModel]
     */
    private fun setupViewModel() {
        viewModel = ViewModelProvider(requireActivity()).get(HingeAngleViewModel::class.java)
    }

    /**
     * Binds all UI components.
     */
    private fun bindUI(view: View) {
        hingeAngleValueView = view.findViewById(R.id.hinge_angle)
        drawingView = view.findViewById(R.id.drawing_view)
        val clearButton = view.findViewById<Button>(R.id.button_clear)
        clearButton.setOnClickListener { clearDrawingSurface() }

        binding.colorsSelector.penGroup.setOnCheckedChangeListener { group, optionId ->
            when (optionId) {
                R.id.button_red -> chooseColor(PaintColors.Red)
                R.id.button_blue -> chooseColor(PaintColors.Blue)
                R.id.button_green -> chooseColor(PaintColors.Green)
                R.id.button_yellow -> chooseColor(PaintColors.Yellow)
                R.id.button_purple -> chooseColor(PaintColors.Purple)
            }
        }
    }

    /**
     * Changes the color for the drawing surface.
     */
    private fun chooseColor(paintColors: PaintColors) {
        drawingView.paintColor = getColor(requireContext(), paintColors.color)
    }

    /**
     * Clears the drawing surface.
     */
    private fun clearDrawingSurface() {
        drawingView.clearDrawingSurface()
        viewModel.setImage(null)
        viewModel.pathList = emptyList()
    }

    /**
     * Restores the drawing surface with the latest known drawing elements
     * (for example, after screen rotation)
     */
    private fun restoreDrawingSurfaceState() {
        drawingView.drawingPathList = viewModel.pathList
        drawingView.paintRadius = viewModel.penRadius
        drawingView.paints = viewModel.paints
        viewModel.selectedPen
            .takeIf { it != 0 }
            ?.let {
                binding.colorsSelector.penGroup.check(it)
            }
    }

    private fun observeHingeAngle() {
        HingeAngleLiveData.get(requireContext()).observe(
            viewLifecycleOwner
        ) { angle ->
            if (angle != UNAVAILABLE_HINGE) {
                hingeAngleValueView.text = getString(R.string.hinge_angle_value, angle)
                drawingView.paintRadius = angle
                currentAngle = angle
            } else {
                hingeAngleValueView.text = getString(R.string.hinge_angle_unavailable)
                drawingView.paintRadius = DEFAULT_HINGE_ANGLE
                currentAngle = DEFAULT_HINGE_ANGLE
            }
        }
    }

    override fun onResume() {
        super.onResume()
        drawingView.attachOnDrawListener {
            saveDrawingState()
        }
    }

    override fun onPause() {
        super.onPause()
        drawingView.detachOnDrawListener()
    }

    /**
     * Save the last known drawing elements
     */
    private fun saveDrawingState() {
        if (isAdded) {
            viewModel.setImage(drawingView.drawToBitmap())
            viewModel.pathList = drawingView.drawingPathList
            viewModel.penRadius = currentAngle
            viewModel.paints = drawingView.paints
            viewModel.selectedPen = binding.colorsSelector.penGroup.checkedRadioButtonId
        }
    }
}
