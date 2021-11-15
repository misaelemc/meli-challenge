package com.mmunoz.meli

import android.app.Application
import com.mmunoz.base.di.ComponentHolder
import com.mmunoz.meli.di.components.AppComponent
import com.mmunoz.meli.di.components.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

open class MeLiApp : Application(), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    protected var _component: AppComponent? = null
    val component: AppComponent
        get() = _component!!

    override fun onCreate() {
        super.onCreate()
        initializeInjector()
    }

    override fun androidInjector(): AndroidInjector<Any> {
        return androidInjector
    }

    override fun onTerminate() {
        super.onTerminate()
        _component = null
    }

    open fun initializeInjector() {
        ComponentHolder.components.clear()
        _component = DaggerAppComponent.builder()
            .application(this)
            .url(getString(R.string.meli_url))
            .build()
            .apply { inject(this@MeLiApp) }
        ComponentHolder.components.add(component)
    }
}