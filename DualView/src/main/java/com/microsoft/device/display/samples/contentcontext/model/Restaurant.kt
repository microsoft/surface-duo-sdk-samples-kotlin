/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.contentcontext.model

import android.os.Parcel
import android.os.Parcelable

data class Restaurant(
    val title: String? = "",
    val imageResourceId: Int = 0,
    val rating: Double = 0.0,
    val voteCount: Int = 0,
    val type: Type,
    val priceRange: Int = 0,
    val description: String? = "",
    val mapImageResourceId: Int = 0
) : Parcelable {

    enum class Type(private val label: String) {
        American("American"),
        Italian("Italian"),
        Thai("Thai"),
        Korean("Korean"),
        FineDine("Fine Dine"),
        Breakfast("Breakfast");

        override fun toString(): String {
            return label
        }
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(title)
        dest.writeInt(imageResourceId)
        dest.writeDouble(rating)
        dest.writeInt(voteCount)
        dest.writeString(type.toString())
        dest.writeInt(priceRange)
        dest.writeString(description)
        dest.writeInt(mapImageResourceId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Restaurant> {
        const val KEY = "Restaurant"

        override fun createFromParcel(source: Parcel): Restaurant {
            val title = source.readString()
            val imageResourceId = source.readInt()
            val rating = source.readDouble()
            val voteCount = source.readInt()
            val type = source.readString() ?: "American"
            val priceRange = source.readInt()
            val description = source.readString()
            val mapImageResourceId = source.readInt()
            return Restaurant(
                title,
                imageResourceId,
                rating, voteCount,
                Type.valueOf(type),
                priceRange,
                description,
                mapImageResourceId
            )
        }

        override fun newArray(size: Int): Array<Restaurant?> {
            return arrayOfNulls(size)
        }
    }
}
