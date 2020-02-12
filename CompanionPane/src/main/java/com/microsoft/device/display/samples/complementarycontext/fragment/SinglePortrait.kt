/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.complementarycontext.fragment

import android.os.Bundle
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.microsoft.device.display.samples.complementarycontext.adapter.PagerAdapter
import com.microsoft.device.display.samples.complementarycontext.R
import com.microsoft.device.display.samples.complementarycontext.Slide

class SinglePortrait : BaseFragment(), OnPageChangeListener {
    private lateinit var viewPager: ViewPager
    private lateinit var fragments: SparseArray<SlideFragment>
    private var currentPosition = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_single_portrait, container, false)
        viewPager = view.findViewById(R.id.pager)
        val pagerAdapter = PagerAdapter(childFragmentManager, fragments)
        viewPager.also {
            it.adapter = pagerAdapter
            it.currentItem = currentPosition
            it.addOnPageChangeListener(this)
        }
        return view
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            viewPager.setCurrentItem(currentPosition, false)
        }
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) { //
    }

    override fun onPageSelected(position: Int) {
        currentPosition = position
        listener?.onItemSelected(position)
    }

    override fun onPageScrollStateChanged(state: Int) { //
    }

    fun setCurrentPosition(position: Int) {
        currentPosition = position
    }

    companion object {
        fun newInstance(slides: ArrayList<Slide>?): SinglePortrait {
            return SinglePortrait().apply {
                fragments = SlideFragment.getFragments(slides)
                currentPosition = 0
            }
        }
    }
}
