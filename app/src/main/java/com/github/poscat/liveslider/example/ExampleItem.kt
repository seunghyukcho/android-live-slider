package com.github.poscat.liveslider.example

import android.graphics.Bitmap

/**
 * An example of data type to include in live-slider item.
 *
 * Define the type data you want to include.
 */
data class ExampleItem (
    var title: String,
    var description: String,
    var img: Bitmap
)