/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.draganddrop.fragment

import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.view.DragEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.microsoft.device.display.samples.draganddrop.databinding.DragAndDropTargetLayoutBinding
import com.microsoft.device.display.samples.draganddrop.utils.MimeType
import com.microsoft.device.display.samples.draganddrop.utils.fromValue
import com.microsoft.device.display.samples.draganddrop.utils.remove
import com.microsoft.device.display.samples.draganddrop.utils.removeViews
import com.microsoft.device.display.samples.draganddrop.utils.replaceWith
import com.microsoft.device.display.samples.draganddrop.utils.setDragAndDropImageURI
import com.microsoft.device.display.samples.draganddrop.utils.setElevation
import com.microsoft.device.display.samples.draganddrop.utils.updateColorAndElevation

/**
 * The Fragment implementation containing the drop zone
 */
class DropTargetFragment : Fragment(), View.OnDragListener {
    companion object {
        private const val HINT_ELEVATION = 4f
        private const val DEFAULT_ELEVATION = 0f
        fun newInstance(): DropTargetFragment = DropTargetFragment()
    }

    private lateinit var binding: DragAndDropTargetLayoutBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DragAndDropTargetLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnDragListeners()
    }

    private fun setOnDragListeners() {
        binding.dropImageHint.setOnDragListener(this)
        binding.dropTextHint.setOnDragListener(this)
    }

    override fun onDrag(view: View, event: DragEvent): Boolean {
        val mimeTypeValue = event.clipDescription?.getMimeType(0)
        val mimeType = MimeType.fromValue(mimeTypeValue)
        return when (event.action) {
            DragEvent.ACTION_DRAG_STARTED -> onDragStarted(mimeType)
            DragEvent.ACTION_DRAG_ENTERED -> onDragEntered(mimeType)
            DragEvent.ACTION_DROP -> onDrop(view, mimeType, event)
            DragEvent.ACTION_DRAG_ENDED -> onDragEnded(mimeType)
            DragEvent.ACTION_DRAG_LOCATION,
            DragEvent.ACTION_DRAG_EXITED -> true // Ignore events
            else -> false
        }
    }

    private fun onDragStarted(mimeType: MimeType?): Boolean {
        return when (mimeType) {
            MimeType.IMAGE_JPEG, MimeType.TEXT_PLAIN -> {
                setBackgroundColor(mimeType)
                true
            }
            else -> false
        }
    }

    private fun onDragEntered(mimeType: MimeType?): Boolean {
        return mimeType?.let {
            setBackgroundColor(it)
            true
        } ?: false
    }

    private fun onDrop(view: View, mimeType: MimeType?, event: DragEvent): Boolean {
        if (!acceptDropEvent(view, mimeType)) {
            onDragEnded(mimeType)
            return false
        }

        when (mimeType) {
            MimeType.IMAGE_JPEG -> handleImageDrop(event)
            MimeType.TEXT_PLAIN -> handleTextDrop(event)
        }

        return onDragEnded(mimeType)
    }

    private fun acceptDropEvent(view: View, mimeType: MimeType?): Boolean {
        val targetView = when (mimeType) {
            MimeType.IMAGE_JPEG -> binding.dropImageHint
            MimeType.TEXT_PLAIN -> binding.dropTextHint
            else -> null
        }

        return targetView == view
    }

    private fun onDragEnded(mimeType: MimeType?): Boolean {
        mimeType?.let {
            clearBackgroundColor(it)
        } ?: run {
            onDragEnded(MimeType.TEXT_PLAIN)
            onDragEnded(MimeType.IMAGE_JPEG)
        }

        return true
    }

    /**
     * Sets the background color to [Color.GRAY] for the drop zone depending on the given mime type
     */
    private fun setBackgroundColor(mimeType: MimeType) {
        val colorFilter = PorterDuffColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN)
        when (mimeType) {
            MimeType.IMAGE_JPEG -> {
                binding.dropImageHint.updateColorAndElevation(colorFilter, HINT_ELEVATION)
                setElevation(HINT_ELEVATION, binding.imageHintImageView, binding.imageHintTextView)
            }

            MimeType.TEXT_PLAIN -> {
                binding.dropTextHint.updateColorAndElevation(colorFilter, HINT_ELEVATION)
                setElevation(HINT_ELEVATION, binding.textHintImageView, binding.textHintTextView)
            }
        }
    }

    /**
     * Clears the background color for the drop zone depending on the given mime type
     */
    private fun clearBackgroundColor(mimeType: MimeType) {
        when (mimeType) {
            MimeType.IMAGE_JPEG -> {
                binding.dropImageHint.updateColorAndElevation(null, DEFAULT_ELEVATION)
                setElevation(
                    DEFAULT_ELEVATION,
                    binding.imageHintImageView,
                    binding.imageHintTextView
                )
            }

            MimeType.TEXT_PLAIN -> {
                binding.textHintImageView
                binding.dropTextHint.updateColorAndElevation(null, DEFAULT_ELEVATION)
                setElevation(DEFAULT_ELEVATION, binding.textHintImageView, binding.textHintTextView)
            }
        }
    }

    /**
     * Adds the text from the [DragEvent.getClipData] to the drop zone
     *
     * @param event The [DragEvent] containing the text that will be added to the drop zone
     */
    private fun handleTextDrop(event: DragEvent) {
        val item = event.clipData.getItemAt(0)
        val dragData = item.text.toString()
        var sourceView = event.localState as? View
        sourceView?.let {
            it.remove()
        } ?: run {
            sourceView = TextView(requireContext()).apply {
                text = dragData
            }
        }

        removeViews(binding.textHintImageView, binding.textHintTextView)
        binding.emptyText.replaceWith(sourceView)
        sourceView?.setOnLongClickListener(null)
        binding.dropTextHint.setOnDragListener(null)
    }

    /**
     * Adds the image from the [DragEvent.getClipData] to the drop zone
     *
     * @param event The [DragEvent] containing the image that will be added to the drop zone
     */
    private fun handleImageDrop(event: DragEvent) {
        val item = event.clipData.getItemAt(0)
        var sourceView = event.localState as? View
        sourceView?.let {
            // Remove the local image view, source view is null if drop from another app
            it.remove()
        } ?: run {
            sourceView = ImageView(requireContext()).apply {
                setDragAndDropImageURI(requireActivity(), event, item)
            }
        }

        removeViews(binding.imageHintTextView, binding.imageHintImageView)
        binding.emptyImage.replaceWith(sourceView)
        sourceView?.setOnLongClickListener(null)
        binding.dropImageHint.setOnDragListener(null)
    }
}
