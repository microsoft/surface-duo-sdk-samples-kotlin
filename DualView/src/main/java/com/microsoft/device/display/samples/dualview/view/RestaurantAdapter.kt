/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.dualview.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.microsoft.device.display.samples.dualview.R
import com.microsoft.device.display.samples.dualview.model.Restaurant
import com.microsoft.device.display.samples.dualview.util.formatPriceRange
import com.microsoft.device.display.samples.dualview.util.formatRating
import com.microsoft.device.display.samples.dualview.view.SelectedViewModel.Companion.NO_ITEM_SELECTED

/**
 * [RecyclerView.Adapter] implementation for the restaurant items
 */
class RestaurantAdapter(
    context: Context,
    private val viewModel: SelectedViewModel,
    private val onClickRestaurantItem: (item: Restaurant) -> Unit
) : RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder>() {
    private val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        RestaurantViewHolder(layoutInflater.inflate(R.layout.restaurant_item, parent, false))

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        holder.bind(
            viewModel.getItem(position)!!,
            onClickRestaurantItem,
            position == viewModel.selectedPosition.value
        )
    }

    override fun getItemCount() = viewModel.listItems.size

    fun selectItem(pos: Int) {
        viewModel.selectedPosition.value?.takeIf { it != NO_ITEM_SELECTED }?.let {
            notifyItemChanged(it)
        }
        viewModel.setSelectedPosition(pos)
        notifyItemChanged(pos)
    }

    /**
     * [RecyclerView.ViewHolder] implementation for the Restaurant item
     */
    class RestaurantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView = itemView.findViewById<ImageView>(R.id.item_image)
        private val titleView = itemView.findViewById<TextView>(R.id.item_title)
        private val ratingView = itemView.findViewById<TextView>(R.id.item_rating)
        private val typeView = itemView.findViewById<TextView>(R.id.item_type)
        private val priceView = itemView.findViewById<TextView>(R.id.item_price)
        private val descriptionView = itemView.findViewById<TextView>(R.id.item_description)

        /**
         * Sets the Restaurant data to the corresponding UI component
         */
        fun bind(item: Restaurant, onClick: (item: Restaurant) -> Unit, isSelected: Boolean) {
            imageView.setImageResource(item.imageResourceId)
            titleView.text = item.title
            ratingView.text = formatRating(item.rating, item.voteCount)
            typeView.text = itemView.resources.getString(R.string.restaurant_type, item.type.toString())
            priceView.text = formatPriceRange(item.priceRange)
            descriptionView.text = item.description

            markViewSelection(isSelected)

            itemView.setOnClickListener { onClick(item) }
        }

        /**
         * Changes the text color of the view to black if the cell is selected, otherwise to gray
         */
        private fun markViewSelection(isSelected: Boolean) {
            val colorResId = if (isSelected) {
                R.color.black
            } else {
                R.color.gray_500
            }
            val color = ContextCompat.getColor(itemView.context, colorResId)

            ratingView?.setTextColor(color)
            typeView?.setTextColor(color)
            priceView?.setTextColor(color)
            descriptionView?.setTextColor(color)
        }
    }
}
