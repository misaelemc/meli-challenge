package com.mmunoz.base.data.managers

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class DisposableManager @Inject constructor() {

    private var compositeDisposable: CompositeDisposable? = null

    fun add(disposable: Disposable) {
        if (compositeDisposable == null) {
            compositeDisposable = CompositeDisposable()
        }
        compositeDisposable!!.add(disposable)
    }

    fun dispose() = compositeDisposable?.clear()

    fun clear() {
        compositeDisposable?.clear()
        compositeDisposable = null
    }
}