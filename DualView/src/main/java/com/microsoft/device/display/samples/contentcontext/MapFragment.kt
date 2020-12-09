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
import androidx.fragment.app.activityViewModels
import com.microsoft.device.display.samples.contentcontext.view.MapImageView
import com.microsoft.device.display.samples.contentcontext.view.SelectedViewModel
import com.microsoft.device.display.samples.contentcontext.view.SelectedViewModel.Companion.NO_ITEM_SELECTED
import com.microsoft.device.dualscreen.ScreenInfo
import com.microsoft.device.dualscreen.ScreenInfoListener
import com.microsoft.device.dualscreen.ScreenManagerProvider

class MapFragment : Fragment(), ScreenInfoListener {
    private lateinit var detailToolbar: Toolbar
    private val selectedViewModel: SelectedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layout = inflater.inflate(R.layout.fragment_item_detail, container, false)
        val mapView = layout?.findViewById<MapImageView>(R.id.img_view)

        selectedViewModel.selectedPosition.observe(
            viewLifecycleOwner,
            {
                if (it != NO_ITEM_SELECTED) {
                    val item = selectedViewModel.getItem(it)
                    if (item != null && item.mapImageResourceId != 0) {
                        mapView?.setImageResource(item.mapImageResourceId)
                    } else {
                        mapView?.setImageResource(R.drawable.unselected_map)
                    }
                } else {
                    mapView?.setImageResource(R.drawable.unselected_map)
                }
            }
        )

        detailToolbar = layout.findViewById(R.id.detail_toolbar)

        return layout
    }

    override fun onScreenInfoChanged(screenInfo: ScreenInfo) {
        if (screenInfo.isDualMode()) {
            when (screenInfo.getScreenRotation()) {
                Surface.ROTATION_0, Surface.ROTATION_180 -> {
                    detailToolbar.visibility = View.VISIBLE
                }
                Surface.ROTATION_90, Surface.ROTATION_270 ->
                    detailToolbar.visibility = View.GONE
                else -> {
                    detailToolbar.visibility = View.VISIBLE
                }
            }
        } else {
            detailToolbar.title = resources.getString(R.string.app_name)
            detailToolbar.inflateMenu(R.menu.menu_map)
            detailToolbar.setOnMenuItemClickListener { onMenuItemSelected(it) }
        }
    }

    override fun onStart() {
        super.onStart()
        ScreenManagerProvider.getScreenManager().addScreenInfoListener(this)
    }

    override fun onResume() {
        super.onResume()
        ScreenManagerProvider.getScreenManager().removeScreenInfoListener(this)
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
