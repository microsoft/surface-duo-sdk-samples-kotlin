package com.microsoft.device.display.samples.draganddrop.util

/***
 *  This function is used for multiple null checks
 */
inline fun <T : Any> ifNotNull(vararg elements: T?, closure: (List<T>) -> Unit) {
    if (elements.all { it != null }) {
        closure(elements.filterNotNull())
    }
}
