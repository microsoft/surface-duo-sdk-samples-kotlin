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

class DualPortrait : BaseFragment(), ItemsListFragment.OnItemSelectedListener {

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
        val view = inflater.inflate(R.layout.fragment_items_dual_portrait, container, false)
        showFragment(itemListFragment, R.id.master_dual)
        return view
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            showBackOnActionBar()
            itemListFragment.setSelectedItem(currentSelectedPosition)
        }
    }

    private fun showFragment(fragment: Fragment?, id: Int) {
        childFragmentManager.commit {
            val showFragment = childFragmentManager.findFragmentById(id)
            if (showFragment == null) {
                add(id, fragment!!)
            } else {
                remove(showFragment)
                add(id, fragment!!)
            }
        }
    }

    override fun onItemSelected(i: Item, position: Int) {
        currentSelectedPosition = position
        // Showing ItemDetailFragment on the right screen when the app is in spanned mode
        showFragment(ItemDetailFragment.newInstance(i), R.id.master_detail)
    }

    private fun showBackOnActionBar() {
        (activity as AppCompatActivity?)?.supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(false)
            setHomeButtonEnabled(false)
        }
    }

    override fun onBackPressed(): Boolean {
        return true
    }

    companion object {
        fun newInstance(items: ArrayList<Item>): DualPortrait {
            return DualPortrait().apply {
                this.items = items
            }
        }
    }
}
