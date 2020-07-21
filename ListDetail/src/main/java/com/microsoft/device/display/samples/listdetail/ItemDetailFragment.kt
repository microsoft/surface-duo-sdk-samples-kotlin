/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.listdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.microsoft.device.display.samples.listdetail.model.MovieMock

class ItemDetailFragment : Fragment() {
    private lateinit var movieMock: MovieMock

    companion object {
        internal fun newInstance(movieMock: MovieMock) = ItemDetailFragment().apply {
            arguments = Bundle().apply {
                this.putSerializable(MovieMock.KEY, movieMock)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            movieMock = it.getSerializable(MovieMock.KEY) as MovieMock
        }
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
        val tvTitle = view.findViewById<TextView>(R.id.tvTitle)
        val tvBody = view.findViewById<TextView>(R.id.tvBody)
        val ratingBar = view.findViewById<RatingBar>(R.id.rating)
        val image = view.findViewById<ImageView>(R.id.image)

        tvTitle.text = movieMock.title
        tvBody.text = movieMock.body
        ratingBar.rating = 2f
        image.setImageResource(R.drawable.ic_movie_black_24dp)

        return view
    }
}
