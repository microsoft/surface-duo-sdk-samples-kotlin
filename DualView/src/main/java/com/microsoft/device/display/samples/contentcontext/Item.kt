/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */
package com.microsoft.device.display.samples.contentcontext

import android.graphics.PointF
import android.os.Parcel
import android.os.Parcelable

class Item private constructor(private val body: String?, val location: PointF?) : Parcelable {

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.apply {
            writeString(body)
            writeFloat(location?.x ?: 0.0f)
            writeFloat(location?.y ?: 0.0f)
        }
    }

    override fun toString(): String {
        return body ?: ""
    }

    companion object CREATOR : Parcelable.Creator<Item> {

        const val KEY = "item"

        override fun createFromParcel(parcel: Parcel): Item {
            val title = parcel.readString()
            val x = parcel.readFloat()
            val y = parcel.readFloat()
            val location = PointF(x, y)
            return Item(title!!, location)
        }

        @JvmStatic
        val items: ArrayList<Item>
            get() {
                return ArrayList<Item>().apply {
                    add(Item("New York", PointF(40.7128f, -74.0060f)))
                    add(Item("Seattle", PointF(47.6062f, -122.3425f)))
                    add(Item("Palo Alto", PointF(37.444184f, -122.161059f)))
                    add(Item("San Francisco", PointF(37.7542f, -122.4471f)))
                }
            }

        override fun newArray(size: Int): Array<Item?> {
            return arrayOfNulls(size)
        }
    }
}