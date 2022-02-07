package com.microsoft.device.display.samples.duosamples.navigation

import androidx.fragment.app.FragmentActivity

/**
 * Implement to easily obtain instance of the [MainNavigator]
 */
interface MainNavigatorHost {
    fun getMainNavigator(): MainNavigator
}

fun FragmentActivity.getMainNavigator(): MainNavigator {
    if (this is MainNavigatorHost) {
        return this.getMainNavigator()
    } else {
        throw RuntimeException("Host Activity must implement [NavigationHostActivity]")
    }
}