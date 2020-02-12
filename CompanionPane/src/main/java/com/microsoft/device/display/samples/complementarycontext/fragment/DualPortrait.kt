/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.complementarycontext.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.microsoft.device.display.samples.complementarycontext.adapter.PagerAdapter
import com.microsoft.device.display.samples.complementarycontext.R
import com.microsoft.device.display.samples.complementarycontext.Slide

class DualPortrait : BaseFragment(), OnPageChangeListener, ContextFragment.OnItemSelectedListener {
    private lateinit var slides: ArrayList<Slide>
    private lateinit var viewPager: ViewPager
    private lateinit var contextFragment: ContextFragment
    private var currentPosition = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_dual_portrait, container, false)
        viewPager = view.findViewById(R.id.pager)
        contextFragment.addOnItemSelectedListener(this)
        showFragment(contextFragment)
        setupViewPager()
        return view
    }

    override fun onResume() {
        super.onResume()
        setCurrentPosition()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            setCurrentPosition()
        }
    }

    private fun showFragment(fragment: Fragment) {
        childFragmentManager.commit {
            val showFragment = childFragmentManager.findFragmentById(R.id.all_slides)
            if (showFragment == null) {
                add(R.id.all_slides, fragment)
            } else {
                remove(showFragment)
                add(R.id.all_slides, fragment)
            }
        }
    }

    private fun setupViewPager() {
        val slideFragments = SlideFragment.getFragments(slides)
        val pagerAdapter = PagerAdapter(childFragmentManager, slideFragments)
        viewPager.also {
            it.adapter = pagerAdapter
            it.setCurrentItem(currentPosition, false)
            it.addOnPageChangeListener(this)
        }
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

    override fun onPageScrollStateChanged(state: Int) {}

    override fun onPageSelected(position: Int) {
        contextFragment.setCurrentItem(position)
        currentPosition = position
        listener?.onItemSelected(position)
    }

    override fun onItemSelected(position: Int) {
        viewPager.currentItem = position
        currentPosition = position
        listener?.onItemSelected(position)
    }

    private fun setCurrentPosition() {
        contextFragment.setCurrentItem(currentPosition)
        viewPager.setCurrentItem(currentPosition, false)
    }

    fun setCurrentPosition(position: Int) {
        currentPosition = position
    }

    companion object {
        fun newInstance(slides: ArrayList<Slide>): DualPortrait {
            return DualPortrait().apply {
                this.slides = slides
                this.currentPosition = 0
                this.contextFragment = ContextFragment.newInstance(slides)
            }
        }
    }
}
