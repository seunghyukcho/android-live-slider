package com.github.poscat.liveslider.example

import android.content.Context
import android.graphics.Color
import android.text.Spannable
import android.text.style.BackgroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import com.github.poscat.liveslider.LiveSliderPagerAdapter
import kotlinx.android.synthetic.main.example_page.view.*

/**
 * Inherit our library(LiveSliderPagerAdapter) and implement your CustomPageAdapter.
 */
class ExamplePageAdapter : LiveSliderPagerAdapter<ExampleItem>() {
    override fun createView(context: Context, container: ViewGroup, item: ExampleItem): View {
        val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        /**
         * Create and connect the view xml you want to display in the viewPager.
         */
        val view = inflater.inflate(R.layout.example_page, container, false)


        /**
         * Put the item data into the view object you want.
         */
        view.title.text = item.title                    // Set Title
        view.description.text = item.description        // Set Description

        val span = view.description.text as Spannable   // For text highlighting
        span.setSpan(
            BackgroundColorSpan(Color.parseColor("#B3000000")),
            0, view.description.text.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        view.image.setImageBitmap(item.img)             // Set Image

        return view
    }


    /**
     * Try adding an animation to the view object.
     */
    override fun startAnimation(context: Context, view: View) {
        view.image.startAnimation(AnimationUtils.loadAnimation(context, R.anim.zoom))
        view.description.startAnimation(AnimationUtils.loadAnimation(context, R.anim.show))
    }
    override fun stopAnimation(context: Context, view: View) {
        view.description.clearAnimation()
        view.description.visibility = View.INVISIBLE
        view.image.clearAnimation()
    }
}