/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.masterdetail.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.fragment.app.Fragment

import com.microsoft.device.display.samples.masterdetail.Item
import com.microsoft.device.display.samples.masterdetail.R
import kotlinx.android.synthetic.main.fragment_item_detail.view.*

class ItemDetailFragment : Fragment() {
    private lateinit var item: Item

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        item = arguments?.getSerializable("item") as Item
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(
                R.layout.fragment_item_detail,
                container,
                false
        )
        val tvTitle = view.tvTitle
        val tvBody = view.tvBody
        val ratingBar = view.rating
        val image = view.image

        tvTitle.text = item.title
        tvBody.text = item.body
        image.setImageResource(R.drawable.ic_movie_black_24dp)
        ratingBar.rating = 2f
        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(item: Item): ItemDetailFragment {
            return ItemDetailFragment().apply {
                arguments = Bundle().apply {
                    putSerializable("item", item)
                }
            }
        }
    }
}
