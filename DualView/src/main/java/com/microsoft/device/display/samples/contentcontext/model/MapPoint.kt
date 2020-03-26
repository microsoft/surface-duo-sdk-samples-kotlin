/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.contentcontext.model

import android.os.Parcel
import android.os.Parcelable

class MapPoint internal constructor(val title: String?, var mapImageResourceID: Int = 0) : Parcelable {

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(title)
        dest.writeInt(mapImageResourceID)
    }

    override fun toString(): String {
        title?.let {
            return it
        } ?: run {
            return ""
        }
    }

    companion object CREATOR : Parcelable.Creator<MapPoint> {
        const val KEY = "MapPoint"

        override fun createFromParcel(source: Parcel): MapPoint {
            val title = source.readString()
            val mapImageResourceID = source.readInt()
            return MapPoint(title, mapImageResourceID)
        }

        override fun newArray(size: Int): Array<MapPoint?> {
            return arrayOfNulls(size)
        }
    }
}
