/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.duosamples

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.microsoft.device.display.samples.duosamples.databinding.ItemAppSampleBinding
import com.microsoft.device.display.samples.duosamples.samples.SampleModel

/**
 * RecyclerViewAdapter for the sample list
 */
class SamplesAdapter(
    context: Context,
    private val samples: List<SampleModel>,
    private val onClick: (sample: SampleModel) -> Unit
) : RecyclerView.Adapter<SamplesAdapter.ImageViewHolder>() {

    private val layoutInflater = LayoutInflater.from(context)
    override fun getItemCount() = samples.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val itemBinding = ItemAppSampleBinding.inflate(layoutInflater, parent, false)
        return ImageViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(samples[position], onClick)
    }

    class ImageViewHolder(private val itemBinding: ItemAppSampleBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(sample: SampleModel, onClick: (sample: SampleModel) -> Unit) {
            itemBinding.appIcon.setImageResource(sample.thumbnail)
            itemBinding.appName.text = sample.appName
            itemBinding.appInfo.text = sample.simpleDescription
            itemBinding.root.setOnClickListener { onClick(sample) }
        }
    }
}