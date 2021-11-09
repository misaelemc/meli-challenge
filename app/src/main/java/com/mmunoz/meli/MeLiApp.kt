package com.mmunoz.meli

import android.app.Application
import com.mmunoz.base.di.ComponentHolder
import com.mmunoz.meli.di.components.AppComponent
import com.mmunoz.meli.di.components.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class MeLiApp : Application(), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    private var component: AppComponent? = null

    override fun onCreate() {
        super.onCreate()
        initializeInjector()
    }

    override fun androidInjector(): AndroidInjector<Any> {
        return androidInjector
    }

    private fun initializeInjector() {
        ComponentHolder.components.clear()
        component = DaggerAppComponent.builder()
            .application(this)
            .build()
            .apply { inject(this@MeLiApp) }
        ComponentHolder.components.add(component!!)
    }
}