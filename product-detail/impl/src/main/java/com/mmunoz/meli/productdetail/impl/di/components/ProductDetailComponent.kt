package com.mmunoz.meli.productdetail.impl.di.components

import com.mmunoz.base.di.ComponentHolder
import com.mmunoz.base.di.modules.FactoryModule
import com.mmunoz.meli.productdetail.impl.di.modules.ProductDetailFeatureModule
import com.mmunoz.meli.productdetail.impl.ui.fragments.ProductDetailBottomSheet
import dagger.Component
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Scope

@ProductDetail
@Component(
    dependencies = [ProductDetailComponent.Parent::class],
    modules = [
        FactoryModule::class,
        ProductDetailFeatureModule::class,
        AndroidSupportInjectionModule::class
    ]
)
interface ProductDetailComponent {

    interface Parent {

    }

    fun androidInjector(): DispatchingAndroidInjector<Any>

    @Component.Factory
    interface Factory {
        fun create(parent: Parent): ProductDetailComponent
    }
}

@Scope
@Retention
annotation class ProductDetail

fun ProductDetailBottomSheet.inject() {
    DaggerProductDetailComponent.factory()
        .create(ComponentHolder.component())
        .androidInjector()
        .inject(this)
}