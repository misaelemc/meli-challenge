package com.mmunoz.meli.categories.impl.di.components

import com.mmunoz.base.di.ComponentHolder
import com.mmunoz.base.di.modules.FactoryModule
import com.mmunoz.meli.categories.impl.di.modules.CategoriesNavigationFeatureModule
import com.mmunoz.meli.categories.impl.di.modules.CategoriesNetworkModule
import com.mmunoz.meli.categories.impl.ui.fragments.CategoriesNavigationFragment
import com.mmunoz.meli.search.api.SearchFeatureLoader
import dagger.Component
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import retrofit2.Retrofit
import javax.inject.Scope

@Categories
@Component(
    dependencies = [CategoriesComponent.Parent::class],
    modules = [
        FactoryModule::class,
        CategoriesNetworkModule::class,
        AndroidSupportInjectionModule::class,
        CategoriesNavigationFeatureModule::class
    ]
)
interface CategoriesComponent {

    interface Parent {
        val retrofit: Retrofit
        val searchFeatureLoader: SearchFeatureLoader
    }

    fun androidInjector(): DispatchingAndroidInjector<Any>

    @Component.Factory
    interface Factory {
        fun create(parent: Parent): CategoriesComponent
    }
}

@Scope
@Retention
annotation class Categories

fun CategoriesNavigationFragment.inject() {
    DaggerCategoriesComponent.factory()
        .create(ComponentHolder.component())
        .androidInjector()
        .inject(this)
}