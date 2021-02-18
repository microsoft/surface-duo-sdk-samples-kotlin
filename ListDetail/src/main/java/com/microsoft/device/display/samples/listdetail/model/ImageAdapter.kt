/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.listdetail.model

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.microsoft.device.display.samples.listdetail.R

/**
 * RecyclerViewAdapter for the image gallery
 */
class ImageAdapter(
    context: Context,
    private val images: List<Int>,
    private val onClick: (image: Int) -> Unit
) : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    private val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ImageViewHolder(
            layoutInflater.inflate(R.layout.list_details_layout_image_item, parent, false)
        )

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(images[position], onClick)
    }

    override fun getItemCount() = images.size

    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView = itemView.findViewById(R.id.image_item) as ImageView

        fun bind(image: Int, onClick: (image: Int) -> Unit) {
            imageView.setImageResource(image)
            itemView.setOnClickListener { onClick(image) }
        }
    }
}