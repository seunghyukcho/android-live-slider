package com.github.poscat.liveslider

import android.content.Context
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import me.relex.circleindicator.CircleIndicator
import java.util.*

/**
 * A custom RecyclerView.Adapter for live slider.
 *
 * @param T the type of the Feed item that you want.
 *
 * Use [me.relex.circleindicator.CircleIndicator] for ViewPager indicator
 */
class LiveSliderAdapter<T>(): RecyclerView.Adapter<LiveSliderAdapter<T>.LiveSliderViewHolder>() {
    private lateinit var pagerAdapter : LiveSliderPagerAdapter<T>
    private var delay : Long = 0
    private var period : Long = 0
    private var currentFeed = 0
    private var data : Array<LiveSliderFeed<T>>? = null
    private var autoSwipe : Boolean = false

    /**
     * You can set ViewPager Auto Swipe and Timer Period use these constructor
     *
     * Default delay and period are 4s.
     */
    constructor(pagerAdapter: LiveSliderPagerAdapter<T>) : this(pagerAdapter, false)

    constructor(pagerAdapter: LiveSliderPagerAdapter<T>, autoSwipe: Boolean) : this(pagerAdapter, autoSwipe, 4000, 4000)

    constructor(pagerAdapter: LiveSliderPagerAdapter<T>, autoSwipe: Boolean, delay: Long, period: Long) : this() {
        this.pagerAdapter = pagerAdapter
        this.delay = delay
        this.period = period
        this.autoSwipe = autoSwipe
    }

    override fun getItemId(position: Int) = data?.get(position).hashCode().toLong()

    override fun getItemCount() = data?.size ?: 0

    override fun onBindViewHolder(holder: LiveSliderViewHolder, position: Int) {
        val feed = data?.get(position)

        holder.category.text = feed?.category
        holder.currentPage = 0

        holder.setViewPager(feed,
                animation = currentFeed == position,
                autoSwipe = (currentFeed == position) && autoSwipe)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LiveSliderViewHolder {
        val context = parent.context
        val layoutIdForListItem = R.layout.live_feed
        val inflater = LayoutInflater.from(context)
        val shouldAttachToParentImmediately = false

        val view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately)
        return LiveSliderViewHolder(view, context, pagerAdapter, delay, period)
    }

    /**
     * Call this function to start ViewPager animation when onScrollStateChanged.
     *
     * @param position position of the custom ViewPager currently displayed in the RecycleView.
     */
    fun startAnimation(position: Int) {
        if (position != -1 && currentFeed != position) {
            notifyItemRangeChanged(0, itemCount)
            currentFeed = position
        }
    }

    /**
     * Call this function to set the all RSS-feed data for RecyclerView.
     *
     * @param data the type of the all RSS-feed data array
     */
    fun setData(data: Array<LiveSliderFeed<T>>?) {
        this.data = data
        Log.d("datasize", this.data!!.size.toString())
        notifyDataSetChanged()
    }

    inner class LiveSliderViewHolder(v: View, private val context: Context, private val pagerAdapter: LiveSliderPagerAdapter<T>, private val delay: Long, private val period: Long) : RecyclerView.ViewHolder(v) {
        private val indicator: CircleIndicator = v.findViewById(R.id.indicator)
        private val viewPager: LiveSliderViewPager = v.findViewById(R.id.viewPager)
        private val timer: Timer = Timer()
        private var autoSwipe = false
        private var newPagerAdapter : LiveSliderPagerAdapter<T>? = null

        var currentPage = 0
        var timerTask : TimerTask? = null
        val category : TextView = v.findViewById(R.id.category)

        private fun createTimerTask() : TimerTask {
            val handler = Handler()
            val pageUpdater = Runnable {
                if (currentPage == newPagerAdapter?.count) {
                    currentPage = 0
                }
                viewPager.setCurrentItem(currentPage++, true)
            }

            return object: TimerTask() {
                override fun run() {
                    handler.post(pageUpdater)
                }
            }
        }

        private fun refreshAutoSwipe() {
            timerTask?.cancel()
            if(autoSwipe) {
                timerTask = createTimerTask()
                timer.schedule(timerTask, delay, period)
            }
        }

        private fun refresh(position: Int) {
            refreshAutoSwipe()
            currentPage = position
            newPagerAdapter?.refreshAnimation(position)
        }

        fun setViewPager(data: LiveSliderFeed<T>?, animation: Boolean, autoSwipe: Boolean) {
            val listener = object : ViewPager.OnPageChangeListener {
                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

                override fun onPageSelected(position: Int) {
                    refresh(position)
                }

                override fun onPageScrollStateChanged(state: Int) {}
            }

            newPagerAdapter = pagerAdapter.javaClass.newInstance()
            this.autoSwipe = autoSwipe
            refreshAutoSwipe()

            newPagerAdapter?.setAdapterContext(context)

            viewPager.addOnPageChangeListener(listener)

            newPagerAdapter?.setData(data, animation)
            newPagerAdapter?.setViewContainer(viewPager)
            viewPager.disableScroll(!animation)
            viewPager.adapter = newPagerAdapter

            indicator.setViewPager(viewPager)
            viewPager.post(Runnable {
                listener.onPageSelected(currentPage)
            })
        }
    }
}