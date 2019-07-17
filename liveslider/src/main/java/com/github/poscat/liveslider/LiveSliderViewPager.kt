package com.github.poscat.liveslider

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

/**
 * This custom ViewPager can block scrolling through touch.
 */
class LiveSliderViewPager : ViewPager {
    private var disable = false

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attributes: AttributeSet?) : super(context, attributes)

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        return if (disable) false else super.onInterceptTouchEvent(event)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return if (disable) false else super.onTouchEvent(event)
    }

    /**
     * If you want to block scrolling, use this function.
     *
     * @property disable determines whether to scroll.
     */
    fun disableScroll(disable: Boolean?) {
        // When disable = true not work the scroll and when disble = false work the scroll
        this.disable = disable!!
    }
}