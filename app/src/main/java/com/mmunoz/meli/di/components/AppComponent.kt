package com.mmunoz.meli.di.components

import com.mmunoz.base.di.modules.FactoryModule
import com.mmunoz.meli.MainActivity
import com.mmunoz.meli.MeLiApp
import com.mmunoz.meli.categories.impl.di.components.CategoriesComponent
import com.mmunoz.meli.categories.wiring_impl.CategoriesWiringModule
import com.mmunoz.meli.di.modules.ActivityBuilder
import com.mmunoz.meli.di.modules.NetworkModule
import com.mmunoz.meli.productdetail.impl.di.components.ProductDetailComponent
import com.mmunoz.meli.productdetail.wiring_impl.ProductDetailWiringModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        FactoryModule::class,
        NetworkModule::class,
        ActivityBuilder::class,
        SearchWiringModule::class,
        CategoriesWiringModule::class,
        ProductDetailWiringModule::class,
        AndroidSupportInjectionModule::class
    ]
)
interface AppComponent : AndroidInjector<MeLiApp>, CategoriesComponent.Parent,
    ProductDetailComponent.Parent {

    fun inject(app: MainActivity)

    @Component.Builder
    interface Builder {
        fun build(): AppComponent

        @BindsInstance
        fun application(application: MeLiApp): Builder
    }
}