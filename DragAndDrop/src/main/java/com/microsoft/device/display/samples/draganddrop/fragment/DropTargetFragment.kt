/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.draganddrop.fragment

import android.content.ClipData
import android.content.ClipDescription
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.ParcelFileDescriptor
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.util.component1
import androidx.core.util.component2
import androidx.core.view.isVisible
import androidx.draganddrop.DropHelper
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.microsoft.device.display.samples.draganddrop.R
import com.microsoft.device.display.samples.draganddrop.databinding.DragAndDropTargetLayoutBinding
import java.io.FileInputStream
import java.io.FileNotFoundException

/**
 * The Fragment implementation containing the drop zone
 */
class DropTargetFragment : Fragment() {
    companion object {
        private const val TAG = "DropTargetFragment"
        fun newInstance(): DropTargetFragment = DropTargetFragment()

        // Support external items on Chrome OS Android 9
        private const val MIME_TYPE_EXTERNAL = "application/x-arc-uri-list"
        private const val MIME_TYPE_IMAGE = "image/*"
    }

    private lateinit var binding: DragAndDropTargetLayoutBinding
    private val viewModel: DropViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DragAndDropTargetLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnTextDropListeners()
        setOnImageDropListeners()
        binding.resetButton.setOnClickListener { resetDropTarget() }

        with(viewModel.text.value) {
            if (this.isNullOrBlank()) {
                resetTextTarget()
            } else {
                setTextTarget(this)
            }
        }

        with(viewModel.image.value) {
            if (this == null) {
                resetImageTarget()
            } else {
                setImageTarget(this)
            }
        }
    }

    private fun setOnTextDropListeners() {
        DropHelper.configureView(
            requireActivity(),
            binding.emptyText,
            arrayOf(
                ClipDescription.MIMETYPE_TEXT_PLAIN,
                MIME_TYPE_EXTERNAL
            ),
            DropHelper.Options.Builder()
                .setHighlightColor(requireContext().getColor(R.color.gray))
                .setHighlightCornerRadiusPx(0)
                .build()
        ) { _, payload ->
            val item = payload.clip.getItemAt(0)
            val remaining = payload.partition { it == item }.second

            if (payload.clip.description.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
                handlePlainTextDrop(item)
            }
            remaining
        }
    }

    private fun setOnImageDropListeners() {
        DropHelper.configureView(
            requireActivity(),
            binding.emptyImage,
            arrayOf(
                MIME_TYPE_IMAGE,
                MIME_TYPE_EXTERNAL
            ),
            DropHelper.Options.Builder()
                .setHighlightColor(requireContext().getColor(R.color.gray))
                .setHighlightCornerRadiusPx(0)
                .build()
        ) { _, payload ->
            // Only handle the first ClipData.Item
            val item = payload.clip.getItemAt(0)
            val (_, remaining) = payload.partition { it == item }

            when {
                payload.clip.description.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN) ->
                    handlePlainTextDrop(item)
                else ->
                    handleImageDrop(item)
            }
            remaining
        }
    }

    private fun resetDropTarget() {
        resetTextTarget()
        resetImageTarget()
    }

    private fun resetTextTarget() {
        viewModel.text.value = ""
        binding.dropTextHint.isVisible = true
        binding.emptyText.text = ""
        binding.emptyText.background = AppCompatResources.getDrawable(
            requireContext(),
            R.drawable.dashed_stroke_background
        )
    }

    private fun resetImageTarget() {
        viewModel.image.value = null
        binding.dropImageHint.isVisible = true
        binding.emptyImage.setImageBitmap(null)
        binding.emptyImage.background = AppCompatResources.getDrawable(
            requireContext(),
            R.drawable.dashed_stroke_background
        )
    }

    private fun setTextTarget(text: CharSequence) {
        viewModel.text.value = text
        binding.emptyText.text = text
        binding.emptyText.background = null
        binding.dropTextHint.isVisible = false
    }

    private fun setImageTarget(bitmap: Bitmap) {
        viewModel.image.value = bitmap
        binding.emptyImage.setImageBitmap(bitmap)
        binding.emptyImage.background = null
        binding.dropImageHint.isVisible = false
    }

    private fun handlePlainTextDrop(item: ClipData.Item) {
        val text = if (item.text != null) {
            // The text is contained in the ClipData.Item
            item.text
        } else {
            // The text is in a file pointed to by the ClipData.Item
            getTextFromClipData(item)
        }
        setTextTarget(text)
    }

    private fun handleImageDrop(item: ClipData.Item) {
        item.uri?.let { uri ->
            val bitmap =
                BitmapFactory.decodeStream(requireContext().contentResolver.openInputStream(uri))
            setImageTarget(bitmap)
        } ?: run {
            Log.e(TAG, "Clip data is missing it's URI")
        }
    }

    private fun getTextFromClipData(item: ClipData.Item): String {
        val parcelFileDescriptor: ParcelFileDescriptor? = try {
            requireActivity().contentResolver.openFileDescriptor(item.uri, "r")
        } catch (e: FileNotFoundException) {
            Log.e(TAG, "Clip data is missing it's text")
            null
        }

        if (parcelFileDescriptor == null) {
            Log.e(TAG, "Invalid File Descriptor")
            return ""
        }
        val reader = FileInputStream(parcelFileDescriptor.fileDescriptor).reader()
        return try {
            reader.readText()
        } catch (e: java.lang.Exception) {
            Log.e(TAG, "Unable to read file: ${e.message}")
            ""
        } finally {
            reader.close()
        }
    }
}
