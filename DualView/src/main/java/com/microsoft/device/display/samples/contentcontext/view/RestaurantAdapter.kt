/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.contentcontext.view

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.microsoft.device.display.samples.contentcontext.R
import com.microsoft.device.display.samples.contentcontext.model.Restaurant
import com.microsoft.device.display.samples.contentcontext.util.formatPriceRange
import com.microsoft.device.display.samples.contentcontext.util.formatRating

class RestaurantAdapter(
    context: Context,
    private val viewModel: SelectedViewModel,
    private val onClick: (item: Restaurant) -> Unit
) : RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder>() {

    private val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        RestaurantViewHolder(
            layoutInflater.inflate(R.layout.restaurant_item, parent, false)
        )

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        holder.bind(
            viewModel.getItem(position)!!,
            onClick,
            position == viewModel.selectedPosition.value
        )
    }

    override fun getItemCount() = viewModel.listItems.size

    fun selectItem(pos: Int) {
        viewModel.selectedPosition.value?.takeIf { it != -1 }?.let {
            notifyItemChanged(it)
        }
        viewModel.setSelectedPosition(pos)
        notifyItemChanged(pos)
    }

    class RestaurantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: AppCompatImageView? = itemView.findViewById(R.id.item_image)
        private val titleView: AppCompatTextView? = itemView.findViewById(R.id.item_title)
        private val ratingView: AppCompatTextView? = itemView.findViewById(R.id.item_rating)
        private val typeView: AppCompatTextView? = itemView.findViewById(R.id.item_type)
        private val priceView: AppCompatTextView? = itemView.findViewById(R.id.item_price)
        private val descriptionView: AppCompatTextView? = itemView.findViewById(R.id.item_description)

        fun bind(item: Restaurant, onClick: (item: Restaurant) -> Unit, isSelected: Boolean) {
            imageView?.setImageResource(item.imageResourceId)
            titleView?.text = item.title
            ratingView?.text = formatRating(item.rating, item.voteCount)
            typeView?.text = itemView.resources.getString(R.string.restaurant_type, item.type.toString())
            priceView?.text = formatPriceRange(item.priceRange)
            descriptionView?.text = item.description

            changeSelection(isSelected)

            itemView.setOnClickListener { onClick(item) }
        }

        private fun changeSelection(isSelected: Boolean) {
            val color = if (isSelected) {
                Color.BLACK
            } else {
                itemView.resources.getColor(R.color.gray_500)
            }

            ratingView?.setTextColor(color)
            typeView?.setTextColor(color)
            priceView?.setTextColor(color)
            descriptionView?.setTextColor(color)
        }
    }
}
