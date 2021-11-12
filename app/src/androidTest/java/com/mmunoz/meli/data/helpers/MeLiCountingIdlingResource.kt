package com.mmunoz.meli.data.helpers

import androidx.test.espresso.IdlingResource
import java.util.concurrent.atomic.AtomicInteger

class MeLiCountingIdlingResource constructor(
    private val resourceName: String
) : IdlingResource {

    private val counter = AtomicInteger(0)

    @Volatile
    private var resourceCallback:
            IdlingResource.ResourceCallback? = null

    override fun getName() = resourceName

    override fun isIdleNow() = counter.get() == 0

    override fun registerIdleTransitionCallback(
        resourceCallback: IdlingResource.ResourceCallback
    ) {
        this.resourceCallback = resourceCallback
    }

    fun increment() {
        counter.getAndIncrement()
    }

    fun decrement() {
        val counterVal = counter.decrementAndGet()
        if (counterVal == 0) {
            resourceCallback?.onTransitionToIdle()
        } else if (counterVal < 0) {
            throw IllegalStateException(
                "Counter has been corrupted!"
            )
        }
    }
}

object TestIdlingResource {

    private const val RESOURCE = "GLOBAL"

    @JvmField
    val countingIdlingResource = MeLiCountingIdlingResource(RESOURCE)

    fun increment() {
        countingIdlingResource.increment()
    }

    fun decrement() {
        if (!countingIdlingResource.isIdleNow) {
            countingIdlingResource.decrement()
        }
    }
}