/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.draganddrop.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

import com.microsoft.device.display.samples.draganddrop.R
import com.microsoft.device.display.samples.draganddrop.util.ifNotNull

class AdaptiveFragment : Fragment() {
    private var dragSourceFragment: DragSourceFragment? = null
    private var dropTargetFragment: DropTargetFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dragSourceFragment = DragSourceFragment.newInstance()
        dropTargetFragment = DropTargetFragment.newInstance()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val bundle = this.arguments
        val layoutId = if (bundle != null)
            this.arguments!!.getInt(KEY_LAYOUT_ID, R.layout.fragment_single_portrait)
        else
            R.layout.fragment_single_portrait
        val view = inflater.inflate(layoutId, container, false)

        val fragmentManager = this.childFragmentManager

        ifNotNull(dragSourceFragment, dropTargetFragment) {
            (dragSourceFragment, dropTargetFragment) ->
            fragmentManager.beginTransaction()
                    .add(R.id.drag_source_container, dragSourceFragment)
                    .add(R.id.drop_target_container, dropTargetFragment)
                    .commit()
        }
        return view
    }

    companion object {
        const val KEY_LAYOUT_ID = "layoutId"

        fun newInstance(): AdaptiveFragment {
            return AdaptiveFragment()
        }
    }
}
