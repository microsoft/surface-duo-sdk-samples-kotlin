/*
 *
 *  Copyright (c) Microsoft Corporation. All rights reserved.
 *  Licensed under the MIT License.
 *
 */
package com.microsoft.device.display.samples.widget.service

import retrofit2.Call
import retrofit2.http.GET

interface WidgetApi {
    @get:GET(".")
    val stringResponse: Call<String?>?
}