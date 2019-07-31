# Android Live Slider

[![Build Status](https://travis-ci.org/shhj1998/android-live-slider.svg?branch=master)](https://travis-ci.org/shhj1998/android-live-slider)
[![Jitpack](https://jitpack.io/v/shhj1998/android-live-slider.svg)](https://jitpack.io/#shhj1998/android-live-slider)
![Downloads](https://jitpack.io/v/shhj1998/android-live-slider/month.svg)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

Awesome Recyclerview library that supports live animations and auto swipe with ViewPager.


## Requirements

- Minimum SDK Version : 24
- Recommended SDK Version : 29


## Preview

<img src="https://github.com/shhj1998/android-live-slider/blob/master/screenshot/live-slider.gif" width="40%" height="40%"/>


## Getting Start

### Dependency Setting

#### Step 1. Add the JitPack repository in your root project's `build.gradle` file :

```gradle
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

#### Step 2. Add our library dependency on your project `build.gradle` file.

```gradle
dependencies {
    implementation 'com.github.shhj1998:android-live-slider:Tag'
}
```

### Implementation

#### Step 1. Create a ViewPager layout like [`example_page.xml`](https://github.com/shhj1998/android-live-slider/blob/master/app/src/main/res/layout/example_page.xml) which will be inside your ViewPager :

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

#### Step 2. Define your content type class.

```kotlin
data class ExampleItem (
    var title: String,
    var description: String,
    var img: Bitmap,
    ...
)
```

#### Step 3. Implement your customer PagerAdapter by inheriting the abstract class `LiveSliderPagerAdapter`.

```kotlin
class ExamplePageAdapter : LiveSliderPagerAdapter<ExampleItem, String>() {
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

#### Step 4. Apply the LiveSliderAdapter and your custom LiveSliderPagerAdapter on your RecycleView.

```kotlin
mRecyclerView = findViewById(R.id.recycler_view)
...

// definition of your recyclerview adapter
mExampleAdapter = LiveSliderAdapter(applicationContext, ExamplePageAdapter(), true)
mExampleAdapter.setHasStableIds(true)

mRecyclerView.adapter = mExampleAdapter

// If the ViewPager shown in RecycleView changes, start the animation.
mRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        val layoutManager = recyclerView.layoutManager as LinearLayoutManager
        var animationItemPosition = layoutManager.findFirstVisibleItemPosition()
	
	mFeedAdapter.startAnimation(animationItemPosition)
    }
})
```

#### Step 5. Set your recyclerview data with your contents. 
When you call the `setFeedData()` function with parsed contents data, it applies immediately to the `live-slider` recyclerview.

```kotlin
var mSampleData: Array<LiveSliderFeed<ExampleItem, String>>

...

mExampleAdapter!!.setFeedData(mSampleData)
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
    
