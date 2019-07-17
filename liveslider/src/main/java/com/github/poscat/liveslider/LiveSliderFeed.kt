package com.github.poscat.liveslider

/**
 * Feed containing *items*.
 *
 * @param T the type of the item in your Feed.
 */
class LiveSliderFeed<T> {
    var category = "example"
    var items : ArrayList<T>? = null
}