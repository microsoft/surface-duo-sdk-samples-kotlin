package com.microsoft.device.display.samples.twopage.utils

import androidx.test.espresso.IdlingResource
import androidx.test.espresso.IdlingResource.ResourceCallback
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.SimpleOnPageChangeListener

class ViewPagerIdlingResource(viewPager: ViewPager) : IdlingResource {
    @Volatile
    private var isIdle = true // Default to idle since we can't query the scroll state.
    private var resourceCallback: ResourceCallback? = null

    init {
        viewPager.addOnPageChangeListener(ViewPagerListener())
    }

    override fun getName(): String {
        return ViewPagerIdlingResource::class.java.simpleName
    }

    override fun isIdleNow(): Boolean {
        return isIdle
    }

    override fun registerIdleTransitionCallback(resourceCallback: ResourceCallback) {
        this.resourceCallback = resourceCallback
    }

    private inner class ViewPagerListener : SimpleOnPageChangeListener() {
        override fun onPageScrollStateChanged(state: Int) {
            isIdle = (state == ViewPager.SCROLL_STATE_IDLE || state == ViewPager.SCROLL_STATE_DRAGGING)
            if (isIdle) {
                resourceCallback?.onTransitionToIdle()
            }
        }
    }
}