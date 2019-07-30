package com.github.poscat.liveslider.example

import android.content.Context
import android.widget.Toast
import com.github.poscat.liveslider.LiveSliderAdapter
import com.github.poscat.liveslider.LiveSliderFeed
import com.github.poscat.liveslider.LiveSliderPagerAdapter

class ExampleRecyclerViewAdapter(context: Context, pagerAdapter: LiveSliderPagerAdapter<ExampleItem, String>): LiveSliderAdapter<ExampleItem, String>(context, pagerAdapter, true) {
    override fun setHolderListener(holder: LiveSliderViewHolder, feed: LiveSliderFeed<ExampleItem, String>, context: Context) {
        holder.category.setOnClickListener {
            Toast.makeText(context, feed.id, Toast.LENGTH_SHORT).show()
        }
    }
}