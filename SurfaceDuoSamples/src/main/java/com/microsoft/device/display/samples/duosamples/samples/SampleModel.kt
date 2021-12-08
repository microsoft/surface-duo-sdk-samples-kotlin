/*
 *
 *  * Copyright (c) Microsoft Corporation. All rights reserved.
 *  * Licensed under the MIT License.
 *  *
 *
 */

package com.microsoft.device.display.samples.duosamples.samples

/**
 * UI model used to display the sample list and details.
 */
class SampleModel(
    val type: Sample,
    val appName: String,
    val thumbnail: Int,
    val simpleDescription: String,
    val detailsImage: Int,
    val detailsDescription: String,
    val gitHubUrl: String,
    var isSelected: Boolean
)
