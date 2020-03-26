/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.contentcontext.model

import com.microsoft.device.display.samples.contentcontext.R

import java.util.ArrayList

object DataProvider {
    val mapPoints: ArrayList<MapPoint>
        get() {
            val mapPoints = ArrayList<MapPoint>()
            mapPoints.add(MapPoint("New York", R.drawable.new_york))
            mapPoints.add(MapPoint("Seattle", R.drawable.seattle))
            mapPoints.add(MapPoint("Palo Alto", R.drawable.palo_alto))
            mapPoints.add(MapPoint("San Francisco", R.drawable.san_francisco))
            return mapPoints
        }
}
