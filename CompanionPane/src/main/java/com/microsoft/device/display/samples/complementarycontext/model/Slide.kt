/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.complementarycontext.model

class Slide internal constructor(val content: String, val note: String) {
    fun equals(obj: Slide?): Boolean {
        return if (obj != null) {
            this.note == obj.note && this.content == obj.content
        } else {
            false
        }
    }
}
