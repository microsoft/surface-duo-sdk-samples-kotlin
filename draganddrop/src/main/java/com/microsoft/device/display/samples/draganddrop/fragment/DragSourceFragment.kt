/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.draganddrop.fragment

import android.content.ClipData
import android.content.ClipDescription
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment

import com.microsoft.device.display.samples.draganddrop.R

class DragSourceFragment : Fragment(), View.OnLongClickListener {

    companion object {
        fun newInstance(): DragSourceFragment {
            return DragSourceFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.drag_source_layout, container, false)

        val dragTextView = view.findViewById<TextView>(R.id.drag_text_view)
        val dragImageView = view.findViewById<ImageView>(R.id.drag_image_view)

        dragTextView.tag = "text_view"
        dragImageView.tag = "image_view"
        dragTextView.setOnLongClickListener(this)
        dragImageView.setOnLongClickListener(this)

        return view
    }

    override fun onLongClick(v: View): Boolean {
        val item = ClipData.Item(v.tag as CharSequence)
        val mimeTypes = arrayOfNulls<String>(1)

        if (v is ImageView) {
            mimeTypes[0] = "image/jpeg"
        } else if (v is TextView) {
            mimeTypes[0] = ClipDescription.MIMETYPE_TEXT_PLAIN
        }
        val data = ClipData(v.tag.toString(), mimeTypes, item)
        val dragShadowBuilder = View.DragShadowBuilder(v)
        v.startDragAndDrop(data, dragShadowBuilder, v, 0)
        return true
    }
}