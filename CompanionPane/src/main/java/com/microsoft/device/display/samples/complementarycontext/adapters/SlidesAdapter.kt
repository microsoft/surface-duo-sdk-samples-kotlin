/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.complementarycontext.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

import com.microsoft.device.display.samples.complementarycontext.R
import com.microsoft.device.display.samples.complementarycontext.model.Slide

class SlidesAdapter : ListAdapter<Slide, SlidesAdapter.SlideViewHolder>(Comparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SlideViewHolder {
        val slide = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_slides, parent, false)

        return SlideViewHolder(slide)
    }

    override fun onBindViewHolder(holder: SlideViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class SlideViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var slide: TextView = itemView.findViewById(R.id.slide)

        fun bind(slide: Slide) {
            this.slide.text = slide.content
        }
    }
}