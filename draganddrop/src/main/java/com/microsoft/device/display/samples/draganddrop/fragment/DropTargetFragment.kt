/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.draganddrop.fragment

import android.content.ContentResolver
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.view.DragEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment

import com.microsoft.device.display.samples.draganddrop.R

class DropTargetFragment : Fragment(), View.OnDragListener {
    private lateinit var imageDropContainer: RelativeLayout
    private lateinit var textDropContainer: RelativeLayout
    private lateinit var imageHintContainer: ConstraintLayout
    private lateinit var textHintContainer: ConstraintLayout

    companion object {
        fun newInstance(): DropTargetFragment {
            return DropTargetFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.drop_target_layout, container, false)

        imageHintContainer = view.findViewById(R.id.drop_image_hint)
        textHintContainer = view.findViewById(R.id.drop_text_hint)

        imageDropContainer = view.findViewById(R.id.drop_image_container)
        textDropContainer = view.findViewById(R.id.drop_text_container)
        imageDropContainer.setOnDragListener(this)
        textDropContainer.setOnDragListener(this)

        return view
    }

    override fun onDrag(v: View, event: DragEvent): Boolean {
        val action = event.action
        var mimeType = ""

        if (event.clipDescription != null) {
            mimeType = event.clipDescription.getMimeType(0)
        }

        when (action) {
            DragEvent.ACTION_DRAG_STARTED -> {
                if (mimeType == "") {
                    return false
                }
                if (isImage(mimeType) || isText(mimeType)) {
                    setBackgroundColor(mimeType)
                    return true
                }
                return false
            }

            DragEvent.ACTION_DRAG_ENTERED -> {
                setBackgroundColor(mimeType)
                return true
            }

            DragEvent.ACTION_DROP -> {
                if (isText(mimeType)) {
                    handleTextDrop(event)
                    v.elevation = 1f
                } else if (isImage(mimeType)) {
                    handleImageDrop(event)
                    v.elevation = 1f
                }
                clearBackgroundColor(mimeType)
                return true
            }

            DragEvent.ACTION_DRAG_ENDED -> {
                clearBackgroundColor()
                return true
            }

            DragEvent.ACTION_DRAG_LOCATION, DragEvent.ACTION_DRAG_EXITED ->
                // Ignore events
                return true

            else -> {
            }
        }
        return false
    }

    private fun setBackgroundColor(mimeType: String) {
        val colorFilter = PorterDuffColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN)
        if (isImage(mimeType)) {
            imageHintContainer.background.colorFilter = colorFilter
            imageHintContainer.elevation = 4f
            imageHintContainer.invalidate()
        } else if (isText(mimeType)) {
            textHintContainer.background.colorFilter = colorFilter
            textHintContainer.elevation = 4f
            textHintContainer.invalidate()
        }
    }

    private fun clearBackgroundColor(mimeType: String) {
        if (isImage(mimeType)) {
            imageHintContainer.background.clearColorFilter()
            imageHintContainer.elevation = 0f
            imageHintContainer.invalidate()
        } else if (isText(mimeType)) {
            textHintContainer.background.clearColorFilter()
            textHintContainer.elevation = 0f
            textHintContainer.invalidate()
        }
    }

    private fun clearBackgroundColor() {
        imageHintContainer.background.clearColorFilter()
        imageHintContainer.elevation = 0f
        imageHintContainer.invalidate()
        textHintContainer.background.clearColorFilter()
        textHintContainer.elevation = 0f
        textHintContainer.invalidate()
    }

    private fun isImage(mime: String): Boolean {
        return mime.startsWith("image/")
    }

    private fun isText(mime: String): Boolean {
        return mime.startsWith("text/")
    }

    private fun handleTextDrop(event: DragEvent) {
        val item = event.clipData.getItemAt(0)
        val dragData = item.text.toString()
        var view = event.localState as? View
        // Remove the local text view, vw is null if drop from another app
        if (view != null) {
            val owner = view.parent as ViewGroup
            owner.removeView(view)
        } else {
            val textView = TextView(this.context)
            textView.text = dragData
            view = textView
        }

        textDropContainer.removeAllViews()
        textDropContainer.addView(view)
        view.visibility = View.VISIBLE
    }

    private fun handleImageDrop(event: DragEvent) {
        val item = event.clipData.getItemAt(0)
        var view: View? = event.localState as View
        // Remove the local image view, vw is null if drop from another app
        if (view != null) {
            val owner = view.parent as ViewGroup
            owner.removeView(view)
        } else {
            val imageView = ImageView(this.context)
            val uri = item.uri
            if (ContentResolver.SCHEME_CONTENT == uri.scheme) {
                // Accessing a "content" scheme Uri requires a permission grant.
                ActivityCompat.requestDragAndDropPermissions(this.activity, event)
                        ?: return // Permission could not be obtained.return

                imageView.setImageURI(uri)
            } else {
                // Other schemes (such as "android.resource") do not require a permission grant.
                imageView.setImageURI(uri)
            }

            view = imageView
        }

        imageDropContainer.removeAllViews()
        imageDropContainer.addView(view)
        view.visibility = View.VISIBLE
    }
}
