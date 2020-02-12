/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.complementarycontext.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.GestureDetector
import android.view.ViewGroup
import android.view.MotionEvent
import android.view.View
import android.view.GestureDetector.SimpleOnGestureListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.microsoft.device.display.samples.complementarycontext.adapter.RecyclerViewAdapter
import com.microsoft.device.display.samples.complementarycontext.R
import com.microsoft.device.display.samples.complementarycontext.Slide
import kotlinx.android.synthetic.main.fragment_context_layout.view.*

class ContextFragment : Fragment() {
    private lateinit var slides: ArrayList<Slide>
    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewAdapter: RecyclerViewAdapter
    private lateinit var listener: OnItemSelectedListener
    private var prevSelectedPosition = 0

    interface OnItemSelectedListener {
        fun onItemSelected(position: Int)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_context_layout, container, false)
        recyclerView = view.recycler_view
        setupRecyclerView()
        return view
    }

    fun addOnItemSelectedListener(listener: OnItemSelectedListener) {
        this.listener = listener
    }

    private fun setupRecyclerView() {
        recyclerViewAdapter = RecyclerViewAdapter(recyclerView.context, slides)
        recyclerView.adapter = recyclerViewAdapter
        recyclerView.layoutManager = StaggeredGridLayoutManager(1, OrientationHelper.VERTICAL)
        val gestureDetector = GestureDetector(activity, object : SimpleOnGestureListener() {
            override fun onSingleTapUp(e: MotionEvent): Boolean {
                val childView = recyclerView.findChildViewUnder(e.x, e.y)
                if (childView != null) {
                    val position = recyclerView.getChildLayoutPosition(childView)
                    listener.onItemSelected(position)
                    return true
                }
                return super.onSingleTapUp(e)
            }
        })
        recyclerView.addOnItemTouchListener(object : OnItemTouchListener {
            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                return gestureDetector.onTouchEvent(e)
            }

            override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) { //
            }

            override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) { //
            }
        })
    }

    fun setCurrentItem(position: Int) {
        if (prevSelectedPosition != position) {
            var scrollTo: Int
            scrollTo = if (prevSelectedPosition - position > 0) {
                position - 1
            } else {
                position + 1
            }
            if (scrollTo < 0) {
                scrollTo = 0
            }
            prevSelectedPosition = position
            recyclerView.smoothScrollToPosition(scrollTo)
            recyclerViewAdapter.setCurrentPosition(position)
        }
    }

    companion object {
        fun newInstance(slides: ArrayList<Slide>): ContextFragment {
            return ContextFragment().apply {
                this.slides = slides
            }
        }
    }
}
