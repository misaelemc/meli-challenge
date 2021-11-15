package com.mmunoz.base.ui.helpers

import androidx.test.espresso.idling.CountingIdlingResource

fun CountingIdlingResource.decrementWithoutErrors() {
    if (!isIdleNow) {
        decrement()
    }
}