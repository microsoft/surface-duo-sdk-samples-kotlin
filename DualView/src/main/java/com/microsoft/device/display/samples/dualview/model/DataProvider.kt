/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.dualview.model

import com.microsoft.device.display.samples.dualview.R

/**
 * Contains all restaurants data
 */
object DataProvider {
    val restaurants: List<Restaurant> = listOf(
        Restaurant(
            "Pestle Rock",
            R.drawable.pestle_rock_image,
            4.4,
            2303304,
            Restaurant.Type.Thai,
            3,
            "Wine bar with upscale small plates in a lofty modern space with a central wine tower & staircase.",
            RestaurantCoordinates(910, 1700)
        ),
        Restaurant(
            "Sam's Pizza",
            R.drawable.sams_pizza_image,
            4.9,
            1343,
            Restaurant.Type.American,
            2,
            "Take-out/delivery chain offering classic & specialty pizzas, wings & breadsticks, plus desserts.",
            RestaurantCoordinates(1049, 1914)
        ),
        Restaurant(
            "Sizzle and Crunch",
            R.drawable.sizzle_crunch_image,
            3.9,
            966,
            Restaurant.Type.Thai,
            2,
            "Eatery with a wood-fired oven turning out European & NW dishes in a white-&-blue cottage-like room.",
            RestaurantCoordinates(1225, 1855)
        ),
        Restaurant(
            "Cantinetta",
            R.drawable.cantinetta_image,
            4.6,
            1322,
            Restaurant.Type.Italian,
            4,
            "Gourmet Neapolitan pies served in a lofty space with casual, industrial-chic decor.",
            RestaurantCoordinates(1145, 1987)
        ),
        Restaurant(
            "Araya's Place",
            R.drawable.arayas_place_image,
            4.6,
            1322,
            Restaurant.Type.Thai,
            2,
            "Araya's Place is the 1st vegan-Thai restaurant in the northwest while supporting local farms.",
            RestaurantCoordinates(1605, 2043)
        ),
        Restaurant(
            "Kimchi Bistro",
            R.drawable.kimchi_bistro_image,
            3.6,
            4565,
            Restaurant.Type.Korean,
            4,
            "Small, no frills Korean restaurant with an extensive menu served in simple digs inside a mall.",
            RestaurantCoordinates(1220, 2200)
        ),
        Restaurant(
            "Topolopompo Restaurant",
            R.drawable.topolopompo_image,
            4.5,
            6001,
            Restaurant.Type.FineDine,
            3,
            "Compact locale with counter service dishing up classic Mediterranean eats such as hummus & falafel.",
            RestaurantCoordinates(1410, 2228)
        ),
        Restaurant(
            "Morsel",
            R.drawable.morsel_image,
            4.7,
            787,
            Restaurant.Type.Breakfast,
            3,
            "Homey cafe with sofas, board games & quiet corners for gourmet coffee & craft biscuit sandwiches.",
            RestaurantCoordinates(1330, 2375)
        )
    )
}

/**
 * The coordinated of the restaurant relative to the background map image
 */
class RestaurantCoordinates(val x: Int, val y: Int)