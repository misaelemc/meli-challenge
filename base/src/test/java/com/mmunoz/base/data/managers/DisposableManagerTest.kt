package com.mmunoz.base.data.managers

import io.reactivex.Observable
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import java.util.concurrent.TimeUnit

@RunWith(MockitoJUnitRunner::class)
class DisposableManagerTest {

    private val disposableManager = DisposableManager()

    @Test
    fun `Verify that the add method create a CompositeDisposable reference`() {
        addMockDisposable()
        Assert.assertTrue(disposableManager.compositeDisposable.size() > 0)
    }

    @Test
    fun `Verify that the dispose method remove all the disposables`() {
        addMockDisposable()
        disposableManager.dispose()
        Assert.assertTrue(disposableManager.compositeDisposable.size() == 0)
    }

    @Test(expected = NullPointerException::class)
    fun `Verify that the clear method remove all the disposables`() {
        addMockDisposable()
        disposableManager.clear()
        Assert.assertNull(disposableManager.compositeDisposable)
    }

    private fun addMockDisposable() {
        val seconds = Observable.interval(1, TimeUnit.SECONDS)
        val disposable = seconds.subscribe()
        disposableManager.add(disposable)
    }
}