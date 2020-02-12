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
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.microsoft.device.display.samples.masterdetail.Item
import com.microsoft.device.display.samples.masterdetail.R

// In this sample, single portrait is equal to double landscape
class SinglePortrait : BaseFragment(), ItemsListFragment.OnItemSelectedListener {

    override var currentSelectedPosition = 0
        set(position) {
            if (field != position) {
                field = position
            }
        }

    private lateinit var itemListFragment: ItemsListFragment
    private lateinit var items: ArrayList<Item>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        itemListFragment = ItemsListFragment.newInstance(items).also {
            it.registerOnItemSelectedListener(this)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_items_single_portrait, container, false)
        showFragment(itemListFragment)
        return view
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            // Current view is detail view
            if (childFragmentManager.backStackEntryCount == 2) {
                showBackOnActionBar(true)
                onBackPressed()
            }
            itemListFragment.setSelectedItem(currentSelectedPosition)
        }
    }

    private fun showFragment(fragment: Fragment) {
        childFragmentManager.commit {
            val showFragment = childFragmentManager.findFragmentById(R.id.master_single)
            if (showFragment == null) {
                add(R.id.master_single, fragment)
            } else {
                hide(showFragment).add(R.id.master_single, fragment)
            }
            addToBackStack(fragment.javaClass.name)
        }
    }

    override fun onItemSelected(i: Item, position: Int) {
        currentSelectedPosition = position
        showBackOnActionBar(true)
        showFragment(ItemDetailFragment.newInstance(i))
    }

    private fun showBackOnActionBar(show: Boolean) {
        (activity as AppCompatActivity?)?.supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(show)
            setHomeButtonEnabled(show)
        }
    }

    override fun onBackPressed(): Boolean {
        // Current view is list view
        if (childFragmentManager.backStackEntryCount == 1) {
            return true
        } else {
            childFragmentManager.popBackStack()
            childFragmentManager.executePendingTransactions()
            // Do not show back on the actionbar when current fragment is ItemsListFragment
            val showFragment = childFragmentManager.findFragmentById(R.id.master_single)
            if (showFragment is ItemsListFragment) {
                showBackOnActionBar(false)
            }
        }
        return false
    }

    companion object {
        fun newInstance(items: ArrayList<Item>): SinglePortrait {
            return SinglePortrait().apply {
                this.items = items
            }
        }
    }
}
