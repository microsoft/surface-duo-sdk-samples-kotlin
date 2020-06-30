/*
 *
 *  * Copyright (c) Microsoft Corporation. All rights reserved.
 *  * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.hingeangle

import android.app.Service
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.microsoft.device.display.samples.hingeangle.model.ViewModel

class SingleScreenFragment : Fragment() {

    enum class PaintColors {
        Red,
        Blue,
        Green,
        Yellow,
        Purple
    }
    private lateinit var drawView: PenDrawView
    private lateinit var textView: TextView
    private var currentAngle: Int = 0
    private val HINGE_ANGLE_SENSOR_NAME = "Hinge Angle"
    private var sensorManager: SensorManager? = null
    private var hingeAngleSensor: Sensor? = null

    // Hinge angle related
    private val hingeAngleSensorListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent) {
            if (event.sensor == hingeAngleSensor) {
                val angle = event.values[0].toInt()
                val viewModel = ViewModelProvider(requireActivity()).get(ViewModel::class.java)
                viewModel.setHingeAngleLiveData(angle)
            }
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        }
    }

    private fun setupSensors() {
        sensorManager = requireActivity().getSystemService(Service.SENSOR_SERVICE) as SensorManager?
        sensorManager?.let {
            val sensorList: List<Sensor> = it.getSensorList(Sensor.TYPE_ALL)
            for (sensor in sensorList) {
                if (sensor.name.contains(HINGE_ANGLE_SENSOR_NAME)) {
                    hingeAngleSensor = sensor
                }
            }
        }
    }

    // Life cycle
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(
            R.layout.fragment_single_screen,
            container,
            false
        )
        textView = view.findViewById(R.id.textView)
        drawView = view.findViewById(R.id.drawView_single)
        val clearButton = view.findViewById<Button>(R.id.button_clear)
        clearButton.setOnClickListener { clearDrawing() }
        val redButton = view.findViewById<Button>(R.id.button_red)
        redButton.setOnClickListener { chooseColor(PaintColors.Red.name) }
        val blueButton = view.findViewById<Button>(R.id.button_blue)
        blueButton.setOnClickListener { chooseColor(PaintColors.Blue.name) }
        val greenButton = view.findViewById<Button>(R.id.button_green)
        greenButton.setOnClickListener { chooseColor(PaintColors.Green.name) }
        val yellowButton = view.findViewById<Button>(R.id.button_yellow)
        yellowButton.setOnClickListener { chooseColor(PaintColors.Yellow.name) }
        val purpleButton = view.findViewById<Button>(R.id.button_purple)
        purpleButton.setOnClickListener { chooseColor(PaintColors.Purple.name) }

        recoverDrawing()
        setupSensors()

        drawView.viewTreeObserver.addOnDrawListener {
            copyDrawBitmapIfAdded()
        }

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val viewModel = ViewModelProvider(requireActivity()).get(ViewModel::class.java)
        viewModel.getHingeAngleLiveData().observe(
            viewLifecycleOwner,
            Observer {
                textView.text = it.toString()
                drawView.setPaintRadius(it)
                currentAngle = it
            }
        )
    }

    override fun onPause() {
        super.onPause()
        if (hingeAngleSensor != null) {
            sensorManager?.unregisterListener(hingeAngleSensorListener, hingeAngleSensor)
        }
    }

    override fun onResume() {
        super.onResume()
        if (hingeAngleSensor != null) {
            sensorManager?.registerListener(hingeAngleSensorListener, hingeAngleSensor, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        drawView.viewTreeObserver.removeOnDrawListener { }
    }

    // Drawing related
    private fun chooseColor(title: String) {
        when (title) {
            PaintColors.Red.name -> drawView.changePaintColor(ContextCompat.getColor(requireActivity().applicationContext, R.color.red))
            PaintColors.Blue.name -> drawView.changePaintColor(ContextCompat.getColor(requireActivity().applicationContext, R.color.blue))
            PaintColors.Green.name -> drawView.changePaintColor(ContextCompat.getColor(requireActivity().applicationContext, R.color.green))
            PaintColors.Yellow.name -> drawView.changePaintColor(ContextCompat.getColor(requireActivity().applicationContext, R.color.yellow))
            PaintColors.Purple.name -> drawView.changePaintColor(ContextCompat.getColor(requireActivity().applicationContext, R.color.purple))
        }
    }

    private fun recoverDrawing() {
        val viewModel = ViewModelProvider(requireActivity()).get(ViewModel::class.java)
        val paths = viewModel.getPathList()
        drawView.setDrawPathList(paths)
        val angle = viewModel.getPenRadius()
        drawView.setPaintRadius(angle)
        val paints = viewModel.getPaints()
        drawView.setPaints(paints)
    }

    private fun clearDrawing() {
        drawView.clearDrawing()
        val viewModel = ViewModelProvider(requireActivity()).get(ViewModel::class.java)
        viewModel.setImageLiveData(null)
        viewModel.setPathList(listOf())
    }

    private fun copyDrawBitmap() {
        val viewModel = ViewModelProvider(requireActivity()).get(ViewModel::class.java)
        val drawBitmap = drawView.getDrawBitmap()
        if (drawBitmap != null) {
            viewModel.setImageLiveData(drawBitmap)
        }
        val pathList = drawView.getDrawPathList()
        if (pathList.isNotEmpty()) {
            viewModel.setPathList(pathList)
        }
        viewModel.setPenRadius(currentAngle)
        val paints = drawView.getPaints()
        viewModel.setPaints(paints)
    }

    private fun copyDrawBitmapIfAdded() {
        if (isAdded) {
            copyDrawBitmap()
        }
    }
}