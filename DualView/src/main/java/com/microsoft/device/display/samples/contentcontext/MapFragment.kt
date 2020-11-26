/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.contentcontext

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.Surface
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.microsoft.device.display.samples.contentcontext.model.Restaurant
import com.microsoft.device.display.samples.contentcontext.view.MapImageView
import com.microsoft.device.dualscreen.layout.ScreenHelper

class MapFragment : Fragment() {

    companion object {
        internal fun newInstance(item: Restaurant?) = MapFragment().apply {
            item?.let {
                arguments = Bundle().apply {
                    this.putParcelable(Restaurant.KEY, item)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layout = inflater.inflate(R.layout.fragment_item_detail, container, false)
        val mapView = layout?.findViewById<MapImageView>(R.id.img_view)

        if (arguments != null) {
            val item = requireArguments().getParcelable<Restaurant>(Restaurant.KEY)
            if (item != null && item.mapImageResourceId != 0) {
                mapView?.setImageResource(item.mapImageResourceId)
            } else {
                mapView?.setImageResource(R.drawable.unselected_map)
            }
        } else {
            mapView?.setImageResource(R.drawable.unselected_map)
        }

        val detailToolbar = layout?.findViewById<Toolbar>(R.id.detail_toolbar)
        activity?.let { activity ->
            if (ScreenHelper.isDualMode(activity)) {
                when (ScreenHelper.getCurrentRotation(activity)) {
                    Surface.ROTATION_0, Surface.ROTATION_180 -> {
                        detailToolbar?.visibility = View.VISIBLE
                    }
                    Surface.ROTATION_90, Surface.ROTATION_270 ->
                        detailToolbar?.visibility = View.GONE
                    else -> {
                        detailToolbar?.visibility = View.VISIBLE
                    }
                }
            } else {
                detailToolbar?.title = activity.resources.getString(R.string.app_name)
                detailToolbar?.inflateMenu(R.menu.menu_map)
                detailToolbar?.setOnMenuItemClickListener { onMenuItemSelected(it) }
            }
        }
        return layout
    }

    private fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.action_list -> {
                activity?.onBackPressed()
                true
            }
            else -> false
        }
    }
}
