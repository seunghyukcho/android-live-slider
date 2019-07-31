package com.github.poscat.liveslider.example

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.poscat.liveslider.LiveSliderAdapter
import com.github.poscat.liveslider.LiveSliderFeed

class MainActivity : AppCompatActivity() {

    /**
     * Define the RecyclerView and the CustomAdapter.
     */
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mExampleAdapter: LiveSliderAdapter<ExampleItem, String>

    /**
     * This is sample data.
     */
    private lateinit var mSampleData: ArrayList<LiveSliderFeed<ExampleItem, String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /**
         * Initialize the RecycleView.
         */
        initRecyclerView()
        mSampleData = ArrayList()

        /**
         * Insert your data as below.
         */
        setDataExample()
        val array = Array(mSampleData.size) { LiveSliderFeed<ExampleItem, String>() } // ArrayList to Array

        /**
         * setData() applies directly to the RecyclerView.
         */
        mExampleAdapter.setFeedData(mSampleData.toArray(array))
    }

    private fun setDataExample() {
        // Set ample data1
        val newItem1 = LiveSliderFeed<ExampleItem, String>()
        newItem1.id = "Animal"
        newItem1.category = "Animal"
        val sampleItem1 = ArrayList<ExampleItem>()
        sampleItem1.add(ExampleItem("CAT", "A cat is a furry animal that has a long tail and sharp claws. Cats are often kept as pets.", BitmapFactory.decodeResource(resources, R.drawable.sample_cat)))
        sampleItem1.add(ExampleItem("DOG", "A dog is a very common four-legged animal that is often kept by people as a pet or to guard or hunt.", BitmapFactory.decodeResource(resources, R.drawable.sample_puppy)))
        sampleItem1.add(ExampleItem("HAMSTER", "A hamster is a small furry animal which is similar to a mouse, and which is often kept as a pet.", BitmapFactory.decodeResource(resources, R.drawable.sample_hamster)))
        sampleItem1.add(ExampleItem("DOLPHIN", "A dolphin is a mammal which lives in the sea and looks like a large fish with a pointed mouth.", BitmapFactory.decodeResource(resources, R.drawable.sample_dolphin)))
        newItem1.items = sampleItem1

        mSampleData.add(newItem1)

        // Set ample data2
        val newItem2 = LiveSliderFeed<ExampleItem, String>()
        newItem2.id = "Food"
        newItem2.category = "Food"
        val sampleItem2 = ArrayList<ExampleItem>()
        sampleItem2.add(ExampleItem("STEAK", "A steak is a large flat piece of beef without much fat on it. You cook it by grilling or frying it.", BitmapFactory.decodeResource(resources, R.drawable.sample_steak)))
        sampleItem2.add(ExampleItem("CAKE", "A cake is a sweet food made by baking a mixture of flour, eggs, sugar, and fat in an oven.", BitmapFactory.decodeResource(resources, R.drawable.sample_cakes)))
        sampleItem2.add(ExampleItem("PIZZA", "A pizza is a flat, round piece of dough covered with tomatoes, cheese, and other savoury food, and then baked in an oven.", BitmapFactory.decodeResource(resources, R.drawable.sample_pizza)))
        sampleItem2.add(ExampleItem("PASTA", "Pasta is a type of food made from a mixture of flour, eggs, and water that is formed into different shapes and then boiled.", BitmapFactory.decodeResource(resources, R.drawable.sample_pasta)))
        newItem2.items = sampleItem2

        mSampleData.add(newItem2)

        // Set ample data3
        val newItem3 = LiveSliderFeed<ExampleItem, String>()
        newItem3.id = "Transportation"
        newItem3.category = "Transportation"
        val sampleItem3 = ArrayList<ExampleItem>()
        sampleItem3.add(ExampleItem("SUBWAY", "A subway is an underground railway.", BitmapFactory.decodeResource(resources, R.drawable.sample_subway)))
        sampleItem3.add(ExampleItem("AIRPLANE", "An airplane is a vehicle with wings and one or more engines that enable it to fly through the air.", BitmapFactory.decodeResource(resources, R.drawable.sample_aircraft)))
        sampleItem3.add(ExampleItem("BUS", "A bus is a large motor vehicle which carries passengers from one place to another.", BitmapFactory.decodeResource(resources, R.drawable.sample_bus)))
        newItem3.items = sampleItem3

        mSampleData.add(newItem3)
    }

    private fun initRecyclerView() {
        mRecyclerView = findViewById(R.id.recycler_view)
        mRecyclerView.itemAnimator = null  // Blink animation cancel(when data changed)
        mRecyclerView.layoutManager = LinearLayoutManager(applicationContext, RecyclerView.VERTICAL, false)
        mRecyclerView.setHasFixedSize(true)

        /**
         * Create CustomAdapter.
         */
        mExampleAdapter = LiveSliderAdapter(applicationContext, ExamplePagerAdapter(), true)
        mExampleAdapter.setHasStableIds(true)

        mRecyclerView.adapter = mExampleAdapter

        mRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            /**
             * If the ViewPager shown in RecycleView changes, start the animation.
             */
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if(newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val animationItemPosition = layoutManager.findFirstCompletelyVisibleItemPosition()

                    mExampleAdapter.startAnimation(animationItemPosition)
                }
            }
        })
    }
}
