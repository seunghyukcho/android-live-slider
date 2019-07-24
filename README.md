# Android Live Slider

[![Build Status](https://travis-ci.org/shhj1998/android-live-slider.svg?branch=master)](https://travis-ci.org/shhj1998/android-live-slider)
[![](https://jitpack.io/v/shhj1998/android-live-slider.svg)](https://jitpack.io/#shhj1998/android-live-slider)


Awesome Recyclerview library that supports live animations and auto swipe with ViewPager.


## Requirements

- Minimum SDK Version : 24
- Recommended SDK Version : 28


## Preview

<img src="https://github.com/shhj1998/android-live-slider/blob/master/screenshot/live-slider.gif" width="40%" height="40%"/>


## Getting Start

### Dependency

> Step 1. Add the JitPack repository to your build file

Add it in your root `build.gradle` at the end of repositories:

```gradle
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

> Step 2. Add the dependency

```gradle
dependencies {
    compile 'com.github.shhj1998:android-live-slider:Tag'
}
```

### Implementation

> Step 1. Create your ViewPager Layout

Add layout for your contents like `example_page.xml`. It will be inside your ViewPager :

```xml
<FrameLayout
    android:id="@+id/page"
    ...>
	
    <ImageView
        android:id="@+id/image"
        .../>

    <TextView
        android:id="@+id/title"
        android:text="Title"
        .../>
    
    <TextView
        android:id="@+id/description"
        android:text="Description"
        .../>
    ...
```

> Step 2. Define your content type class

```kotlin
data class ExampleItem (
    var title: String,
    var description: String,
    var img: Bitmap,
    ...
)
```

> Step 3. Create adpapter

Inherit the abstract class `LiveSliderPagerAdapter` and implement a custom PagerAdapter.

```kotlin
class ExamplePageAdapter : LiveSliderPagerAdapter<ExampleItem>() {
    override fun createView(context: Context, container: ViewGroup, item: ExampleItem): View {
        val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        // Create and connect the view xml you want to display in the viewPager.
        val view = inflater.inflate(R.layout.example_page, container, false)

        // Put the item data into the view object you want.
        view.title.text = item.title                    
        view.image.setImageBitmap(item.img)
        ...

        return view
    }

    // Try adding an animation to the view object.
    override fun startAnimation(context: Context, view: View) {
        view.image.startAnimation(AnimationUtils.loadAnimation(context, R.anim.zoom))
        ...
    }
    override fun stopAnimation(context: Context, view: View) {
        view.image.clearAnimation()
        ...
    }
}
```

> Step 4. Apply the LiveSliderAdapter and your custom LiveSliderPagerAdapter on your RecycleView

Define and initialize the `RecycleView` in the `MainActivity`.

```kotlin
mRecyclerView = findViewById(R.id.recycler_view)
...

// definition of your recyclerview adapter
mExampleAdapter = LiveSliderAdapter(ExamplePageAdapter(), true)
mExampleAdapter!!.setHasStableIds(true)

mRecyclerView!!.adapter = mExampleAdapter

// If the ViewPager shown in RecycleView changes, start the animation.
mRecyclerView!!.addOnScrollListener(object : RecyclerView.OnScrollListener() {
    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        
	// Only execute the animations that you are looking at.
        if (newState == RecyclerView.SCROLL_STATE_IDLE)
            mExampleAdapter!!.startAnimation((recyclerView.layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition())
            
    }
})
```

> Step 5. Set your recyclerview data with your contents.

When you call the `setData()` function with parsed contents data, it applies directly to the `live-slider`.

```kotlin
var mSampleData: Array<LiveSliderFeed<ExampleItem>>

...

mExampleAdapter!!.setData(mSampleData)
```

**Finish!**

More details are in our [example project](app).


## License

    Copyright 2019 POSCAT.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
    
