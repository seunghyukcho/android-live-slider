package com.github.poscat.liveslider

/**
 * Feed containing *items*.
 *
 * @param T the type of the item in your Feed.
 */
class LiveSliderFeed<T, U> {
    var id : U? = null
    var category = "example"
    var items : ArrayList<T>? = null
}