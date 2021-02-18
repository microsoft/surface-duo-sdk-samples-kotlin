/*
 * Copyright (c) Microsoft Corporation. All rights reserved.Licensed under the MIT License.
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
import com.microsoft.device.display.samples.hingeangle.model.HingeAngleLiveData
import com.microsoft.device.display.samples.hingeangle.model.HingeAngleViewModel
import com.microsoft.device.display.samples.hingeangle.model.PaintColors
import com.microsoft.device.display.samples.hingeangle.model.color
import com.microsoft.device.display.samples.hingeangle.views.PenDrawingView

/**
 * Fragment containing the drawing surface and the color selector component.
 */
class SingleScreenFragment : Fragment() {
    private lateinit var viewModel: HingeAngleViewModel
    private lateinit var drawingView: PenDrawingView
    private lateinit var hingeAngleValueView: TextView
    private var currentAngle: Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.hinge_angle_fragment_single_screen, container, false)
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
        val redButton = view.findViewById<Button>(R.id.button_red)
        redButton.setOnClickListener { chooseColor(PaintColors.Red) }
        val blueButton = view.findViewById<Button>(R.id.button_blue)
        blueButton.setOnClickListener { chooseColor(PaintColors.Blue) }
        val greenButton = view.findViewById<Button>(R.id.button_green)
        greenButton.setOnClickListener { chooseColor(PaintColors.Green) }
        val yellowButton = view.findViewById<Button>(R.id.button_yellow)
        yellowButton.setOnClickListener { chooseColor(PaintColors.Yellow) }
        val purpleButton = view.findViewById<Button>(R.id.button_purple)
        purpleButton.setOnClickListener { chooseColor(PaintColors.Purple) }
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
    }

    private fun observeHingeAngle() {
        HingeAngleLiveData.get(requireContext()).observe(
            viewLifecycleOwner,
            {
                hingeAngleValueView.text = it.toString()
                drawingView.paintRadius = it
                currentAngle = it
            }
        )
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
        }
    }
}
