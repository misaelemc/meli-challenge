package com.mmunoz.meli

import com.mmunoz.base.di.ComponentHolder
import com.mmunoz.meli.di.components.DaggerAppComponent

class TestMeLiApp: MeLiApp() {

    init {
        instance = this
    }

    override fun initializeInjector() {
        ComponentHolder.components.clear()
        _component = DaggerAppComponent.builder()
            .application(this)
            .url(getString(R.string.meli_url_test))
            .build()
            .apply { inject(this@TestMeLiApp) }
        ComponentHolder.components.add(component)
    }

    companion object {
        private var instance: TestMeLiApp? = null

        fun getInstance() = instance
    }
}