/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.draganddrop.fragment

import android.content.ClipData
import android.content.ClipDescription
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.DRAG_FLAG_GLOBAL
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.FileProvider
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.DragStartHelper
import androidx.fragment.app.Fragment
import com.microsoft.device.display.samples.draganddrop.databinding.DragAndDropSourceLayoutBinding
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

/**
 * The Fragment implementation containing the drag source zone
 */
class DragSourceFragment : Fragment() {

    companion object {
        fun newInstance(): DragSourceFragment = DragSourceFragment()

        private const val DRAG_FOLDER_NAME = "images"
        private const val DRAG_FILE_NAME = "dragged_image.png"
    }

    private lateinit var binding: DragAndDropSourceLayoutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DragAndDropSourceLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareDragListeners()
    }

    /**
     * Binds all UI components
     */
    private fun prepareDragListeners() {
        DragStartHelper(binding.dragTextView) { targetView, _ ->
            val text = (targetView as TextView).text
            val dragClipData = ClipData.newPlainText(ClipDescription.MIMETYPE_TEXT_PLAIN, text)
            val dragShadowBuilder = View.DragShadowBuilder(targetView)
            targetView.startDragAndDrop(dragClipData, dragShadowBuilder, null, DRAG_FLAG_GLOBAL)
        }.attach()

        DragStartHelper(binding.dragImageView) { view, _ ->
            val folder = File(requireContext().filesDir, DRAG_FOLDER_NAME)
            if (!folder.exists()) {
                folder.mkdir()
            }
            val imageFile = File(folder, DRAG_FILE_NAME)

            if (!imageFile.exists()) {
                ByteArrayOutputStream().use { bos ->
                    (view as ImageView).drawable.toBitmap()
                        .compress(Bitmap.CompressFormat.PNG, 0, bos)
                    FileOutputStream(imageFile).use { fos ->
                        fos.write(bos.toByteArray())
                        fos.flush()
                    }
                }
            }

            val imageUri = FileProvider.getUriForFile(
                requireContext(),
                getFileProviderAuthority(),
                imageFile
            )
            val dragClipData =
                ClipData.newUri(requireContext().contentResolver, DRAG_FOLDER_NAME, imageUri)
            val dragShadow = View.DragShadowBuilder(view)

            view.startDragAndDrop(
                dragClipData,
                dragShadow,
                null,
                // Need to Use the DRAG_FLAG_GLOBAL_URI_READ to allow other apps to read from our
                // content provider. Without it, other apps won't receive the drag events.
                DRAG_FLAG_GLOBAL.or(View.DRAG_FLAG_GLOBAL_URI_READ)
            )
        }.attach()
    }

    private fun getFileProviderAuthority() = requireContext().packageName + ".images"
}
