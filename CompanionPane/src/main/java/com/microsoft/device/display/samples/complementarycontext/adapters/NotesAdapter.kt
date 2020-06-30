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
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.microsoft.device.display.samples.complementarycontext.R
import com.microsoft.device.display.samples.complementarycontext.model.Slide

class NotesAdapter : ListAdapter<Slide, NotesAdapter.NoteViewHolder>(Comparator()) {
    private var slidesPager: ViewPager2? = null

    fun setSlidesPager(slidesPager: ViewPager2) {
        this.slidesPager = slidesPager
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val slide = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_notes, parent, false)

        return NoteViewHolder(slide)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var slideContent: TextView = itemView.findViewById(R.id.slide_content)
        private var slideNote: TextView = itemView.findViewById(R.id.slide_note)
        private var cardView: CardView = itemView.findViewById(R.id.card_view)

        fun bind(slide: Slide, position: Int) {
            slideContent.text = slide.content
            slideNote.text = slide.note

            cardView.setOnClickListener {
                oldSelectionPosition = selectionPosition
                selectionPosition = position
                notifyItemChanged(oldSelectionPosition)
                cardView.isSelected = true
                slidesPager?.setCurrentItem(position, true)
            }

            cardView.isSelected = selectionPosition == position
        }
    }

    companion object {
        var selectionPosition = 0
        var oldSelectionPosition = 0
    }
}