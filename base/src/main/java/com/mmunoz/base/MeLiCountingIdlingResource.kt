package com.mmunoz.base

import androidx.test.espresso.idling.CountingIdlingResource

object TestIdlingResource {

    private const val RESOURCE = "GLOBAL"

    val countingIdlingResource : CountingIdlingResource= CountingIdlingResource(RESOURCE)

    fun increment() {
        countingIdlingResource.increment()
    }

    fun decrement() {
        if (!countingIdlingResource.isIdleNow) {
            countingIdlingResource.decrement()
        }
    }
}