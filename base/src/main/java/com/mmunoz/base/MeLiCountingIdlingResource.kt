package com.mmunoz.base

import androidx.test.espresso.idling.CountingIdlingResource

fun CountingIdlingResource.decrementWithoutErrors() {
    if (!isIdleNow) {
        decrement()
    }
}