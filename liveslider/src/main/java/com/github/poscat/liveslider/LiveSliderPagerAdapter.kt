package com.github.poscat.liveslider

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.core.view.iterator
import androidx.viewpager.widget.PagerAdapter

/**
 * A custom PagerAdapter for live slider.
 *
 * @param T the type of the Feed item that you want.
 */
abstract class LiveSliderPagerAdapter<T> : PagerAdapter() {
    private lateinit var viewContainer : ViewGroup
    private lateinit var context: Context
    private var liveSliderFeed : LiveSliderFeed<T>? = null
    private var animation : Boolean = false

    /**
     * Inherit and implement the two functions below to set up view animation.
     *
     * You can set up animation for each view's objects.
     *
     * @param view view that displayed in ViewPager.
     */
    abstract fun startAnimation(context: Context, view: View)
    abstract fun stopAnimation(context: Context, view: View)

    /**
     * Inherit and implement this function to create *view*
     *
     * You have to create view using inflater and set Feed data(item) at view's objects.
     *
     * @param item one item in Feed items.
     * @return *view* that you create and set Feed data.
     */
    protected abstract fun createView(context: Context, container: ViewGroup, item: T) : View

    fun setData(liveSliderFeed: LiveSliderFeed<T>?, animation: Boolean) {
        this.liveSliderFeed = liveSliderFeed
        this.animation = animation
    }

    fun setAdapterContext(context: Context) {
        this.context = context
    }

    /**
     * Call this function to refresh the animation when the view displayed in ViewPager changed.
     * It will start the animation in the view at *position* and stop the others.
     *
     * @param position position of the view that is displayed in ViewPager.
     */
    fun refreshAnimation(position: Int) {
        for (v in viewContainer.iterator()) {
            if (animation && v.id == position) {
                startAnimation(context, v)
            } else {
                stopAnimation(context, v)
            }
        }
    }

    fun setViewContainer(viewContainer: ViewGroup) {
        this.viewContainer = viewContainer
    }

    override fun instantiateItem(container: ViewGroup, position: Int): View {
        val view = createView(context, container, liveSliderFeed!!.items!![position])

        view.id = position
        container.addView(view)

        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun isViewFromObject(view: View, `object`: Any) = view == `object`

    override fun getCount() = liveSliderFeed?.items?.size ?: 0

    override fun finishUpdate(container: ViewGroup) {
        viewContainer = container
    }
}