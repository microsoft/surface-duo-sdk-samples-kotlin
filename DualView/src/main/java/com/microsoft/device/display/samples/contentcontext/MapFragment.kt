/*
 *
 *  Copyright (c) Microsoft Corporation. All rights reserved.
 *  Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.contentcontext

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Surface
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.microsoft.device.display.samples.contentcontext.model.MapPoint
import com.microsoft.device.display.samples.contentcontext.view.MapImageView
import com.microsoft.device.dualscreen.layout.ScreenHelper

class MapFragment : Fragment() {

    companion object {
        internal fun newInstance(mapPoint: MapPoint) = MapFragment().apply {
            arguments = Bundle().apply {
                this.putParcelable(MapPoint.KEY, mapPoint)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_item_detail, container, false)
        arguments?.let { args ->
            val mapPoint = args.getParcelable<MapPoint>(MapPoint.KEY)
            if (mapPoint != null && mapPoint.mapImageResourceID != 0) {
                val mImageView = view.findViewById<MapImageView>(R.id.img_view)
                mImageView.setImageResource(mapPoint.mapImageResourceID)
                val detailToolbar = view.findViewById<Toolbar>(R.id.detail_toolbar)
                detailToolbar.title = mapPoint.title

                // Handle Toolbar visibility
                activity?.let { activity ->
                    if (ScreenHelper.isDualMode(activity)) {
                        when (ScreenHelper.getCurrentRotation(activity)) {
                            Surface.ROTATION_0, Surface.ROTATION_180 ->
                                detailToolbar.visibility = View.VISIBLE
                            Surface.ROTATION_90, Surface.ROTATION_270 ->
                                detailToolbar.visibility = View.GONE
                        }
                    } else {
                        detailToolbar.title = mapPoint.title
                    }
                }
            }
        }
        return view
    }
}
