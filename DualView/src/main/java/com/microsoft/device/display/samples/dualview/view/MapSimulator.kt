/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.dualview.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Point
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.microsoft.device.display.samples.dualview.R

/**
 * Creates a bitmap that simulates Google Maps. The bitmap is obtained from a background and a set of pins.
 */
class MapSimulator(val context: Context) {
    private val density: Float = context.resources.displayMetrics.density

    private val initialMap: Bitmap by lazy {
        ContextCompat.getDrawable(context, R.drawable.unselected_map)?.toBitmap()!!
    }

    private val selectedPin: Bitmap? by lazy {
        ContextCompat.getDrawable(context, R.drawable.ic_location_selected)?.toBitmap()
    }

    private val unselectedPin: Bitmap? by lazy {
        ContextCompat.getDrawable(context, R.drawable.ic_location_unselected)?.toBitmap()
    }

    /**
     * Generates the simulated Google map as a bitmap using a list of pins. It is posible to provide the index of the selected pin.
     * @param locationList the list of all the pins.
     * @param selectedPosition the index of the selected pin from the pin list.
     */
    fun generateMap(locationList: List<Point>, selectedPosition: Int?): Bitmap {
        val finalBitmap =
            Bitmap.createBitmap(initialMap.width, initialMap.height, initialMap.config)
        val canvas = Canvas(finalBitmap)
        canvas.drawBitmap(initialMap, Matrix(), null)

        locationList.forEachIndexed { index, coordinates ->
            val locationPin = if (index == selectedPosition) {
                selectedPin
            } else {
                unselectedPin
            }

            locationPin?.let { pin ->
                val leftOffset = coordinates.x * density - pin.width / 2
                val topOffset = coordinates.y * density - pin.height
                canvas.drawBitmap(pin, leftOffset, topOffset, null)
            }
        }
        return finalBitmap
    }
}