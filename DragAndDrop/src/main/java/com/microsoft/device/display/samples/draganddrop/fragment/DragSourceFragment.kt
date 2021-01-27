/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.draganddrop.fragment

import android.content.ClipData
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.microsoft.device.display.samples.draganddrop.R
import com.microsoft.device.display.samples.draganddrop.utils.mimeType
import com.microsoft.device.display.samples.draganddrop.utils.value

/**
 * The Fragment implementation containing the drag source zone
 */
class DragSourceFragment : Fragment(), View.OnLongClickListener {

    companion object {
        fun newInstance(): DragSourceFragment = DragSourceFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.drag_and_drop_source_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindUI(view)
    }

    /**
     * Binds all UI components
     */
    private fun bindUI(view: View) {
        val dragTextView = view.findViewById<TextView>(R.id.drag_text_view)
        val dragImageView = view.findViewById<ImageView>(R.id.drag_image_view)
        dragTextView.tag = "text_view"
        dragImageView.tag = "image_view"
        dragTextView.setOnLongClickListener(this)
        dragImageView.setOnLongClickListener(this)
    }

    override fun onLongClick(view: View): Boolean {
        startDragAndDropForView(view)
        return true
    }

    /**
     * Starts the drag and drop action for the given [View]
     *
     * @param view The drag source
     */
    private fun startDragAndDropForView(view: View) {
        val clipDataItem = ClipData.Item(view.tag.toString())
        val mimeTypes = arrayOf(view.mimeType?.value)
        val clipData = ClipData(view.tag.toString(), mimeTypes, clipDataItem)
        view.startDragAndDrop(clipData, View.DragShadowBuilder(view), view, 0)
    }
}
