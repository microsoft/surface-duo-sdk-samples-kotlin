/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.complementarycontext.adapters

import androidx.recyclerview.widget.DiffUtil
import com.microsoft.device.display.samples.complementarycontext.model.Slide

class Comparator : DiffUtil.ItemCallback<Slide>() {
    override fun areItemsTheSame(oldItem: Slide, newItem: Slide): Boolean {
        return oldItem.note == newItem.note
    }

    override fun areContentsTheSame(oldItem: Slide, newItem: Slide): Boolean {
        return oldItem.equals(newItem)
    }
}