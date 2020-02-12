/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.complementarycontext

class Slide private constructor(val title: String, val content: String) {

    companion object {
        val slides: ArrayList<Slide>
            get() {
                val SLIDE = "Slide "
                val CONTENT = "Slide Content "
                val slides = ArrayList<Slide>()
                for (i in 0..9) {
                    slides.add(Slide(SLIDE + (i + 1), CONTENT + (i + 1)))
                }
                return slides
            }
    }
}
