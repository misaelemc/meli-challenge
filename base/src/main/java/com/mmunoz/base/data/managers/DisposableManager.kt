package com.mmunoz.base.data.managers

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class DisposableManager @Inject constructor() {

    private var _compositeDisposable: CompositeDisposable? = null
    val compositeDisposable: CompositeDisposable
        get() = _compositeDisposable!!

    fun add(disposable: Disposable) {
        if (_compositeDisposable == null) {
            _compositeDisposable = CompositeDisposable()
        }
        compositeDisposable.add(disposable)
    }

    fun dispose() = compositeDisposable.clear()

    fun clear() {
        compositeDisposable.clear()
        _compositeDisposable = null
    }
}