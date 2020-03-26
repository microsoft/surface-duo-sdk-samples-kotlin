/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.complementarycontext.model

import java.util.ArrayList

object DataProvider {
    val slides: List<Slide>
        get() {
            val list = ArrayList<Slide>()
            list.add(Slide("Slide 1", "Note 1"))
            list.add(Slide("Slide 2", "Note 2"))
            list.add(Slide("Slide 3", "Note 3"))
            list.add(Slide("Slide 4", "Note 4"))
            list.add(Slide("Slide 5", "Note 5"))
            list.add(Slide("Slide 6", "Note 6"))
            list.add(Slide("Slide 7", "Note 7"))
            list.add(Slide("Slide 8", "Note 8"))
            list.add(Slide("Slide 9", "Note 9"))
            list.add(Slide("Slide 10", "Note 10"))
            return list
        }
}
