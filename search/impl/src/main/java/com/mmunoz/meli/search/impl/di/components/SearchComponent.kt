package com.mmunoz.meli.search.impl.di.components

import com.mmunoz.base.di.ComponentHolder
import com.mmunoz.base.di.modules.FactoryModule
import com.mmunoz.meli.productdetail.api.ProductDetailFeatureLoader
import com.mmunoz.meli.search.impl.di.modules.SearchFeatureModule
import com.mmunoz.meli.search.impl.ui.fragments.SearchFragment
import dagger.Component
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import retrofit2.Retrofit
import javax.inject.Scope

@Search
@Component(
    dependencies = [SearchComponent.Parent::class],
    modules = [
        FactoryModule::class,
        SearchFeatureModule::class,
        AndroidSupportInjectionModule::class
    ]
)
interface SearchComponent {

    interface Parent {
        val retrofit: Retrofit
        val productDetailFeatureLoader: ProductDetailFeatureLoader
    }

    fun androidInjector(): DispatchingAndroidInjector<Any>

    @Component.Factory
    interface Factory {
        fun create(parent: Parent): SearchComponent
    }
}

@Scope
@Retention
annotation class Search

fun SearchFragment.inject() {
    DaggerSearchComponent.factory()
        .create(ComponentHolder.component())
        .androidInjector()
        .inject(this)
}