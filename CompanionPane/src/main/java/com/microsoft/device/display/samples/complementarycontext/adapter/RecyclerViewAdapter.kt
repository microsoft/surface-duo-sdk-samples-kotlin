/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.complementarycontext.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.microsoft.device.display.samples.complementarycontext.R
import com.microsoft.device.display.samples.complementarycontext.Slide
import kotlinx.android.synthetic.main.item_slide.view.*

class RecyclerViewAdapter(context: Context?, private val slides: ArrayList<Slide>) : RecyclerView.Adapter<RecyclerViewAdapter.SlideViewHolder>() {

    private val mLayoutInflater: LayoutInflater = LayoutInflater.from(context)
    private var currentPosition = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SlideViewHolder {
        return SlideViewHolder(mLayoutInflater.inflate(R.layout.item_slide, parent, false))
    }

    override fun onBindViewHolder(holder: SlideViewHolder, position: Int) {
        holder.apply {
            content.text = slides[position].content
            title.text = slides[position].title
            if (currentPosition == position) {
                setSelected(true)
            } else {
                setSelected(false)
            }
        }
    }

    fun setCurrentPosition(position: Int) {
        currentPosition = position
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return slides.size
    }

    class SlideViewHolder internal constructor(view: View) : ViewHolder(view) {
        val content: TextView = view.slide_title
        val title: TextView = view.slide_content
        private val cardView: CardView = view.card_view

        fun setSelected(selected: Boolean) {
            cardView.isSelected = selected
        }
    }
}
