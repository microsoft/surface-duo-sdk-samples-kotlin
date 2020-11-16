/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.contentcontext.model

import com.microsoft.device.display.samples.contentcontext.R

object DataProvider {
    val restaurants: List<Restaurant> = listOf(
        Restaurant(
            "Pestle Rock",
            R.drawable.pestle_rock_image,
            4.4,
            2303,
            Restaurant.Type.Thai,
            3,
            "Wine bar with upscale small plates in a lofty modern space with a central wine tower & staircase.",
            R.drawable.pestle_rock_map
        ),
        Restaurant(
            "Sam's Pizza",
            R.drawable.sams_pizza_image,
            4.9,
            1343,
            Restaurant.Type.American,
            2,
            "Take-out/delivery chain offering classic & specialty pizzas, wings & breadsticks, plus desserts.",
            R.drawable.sams_pizza_map
        ),
        Restaurant(
            "Sizzle and Crunch",
            R.drawable.sizzle_crunch_image,
            3.9,
            966,
            Restaurant.Type.Thai,
            2,
            "Eatery with a wood-fired oven turning out European & NW dishes in a white-&-blue cottage-like room.",
            R.drawable.sizzle_crunch_map
        ),
        Restaurant(
            "Cantinetta",
            R.drawable.cantinetta_image,
            4.6,
            1322,
            Restaurant.Type.Italian,
            4,
            "Gourmet Neapolitan pies served in a lofty space with casual, industrial-chic decor.",
            R.drawable.cantinetta_map
        )
    )
}
