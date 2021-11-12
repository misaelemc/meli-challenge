package com.mmunoz.meli.di.components

import com.mmunoz.base.di.modules.FactoryModule
import com.mmunoz.meli.MeLiApp
import com.mmunoz.meli.categories.impl.di.components.CategoriesComponent
import com.mmunoz.meli.categories.wiring_impl.CategoriesWiringModule
import com.mmunoz.meli.di.modules.ActivityBuilder
import com.mmunoz.meli.di.modules.NetworkModule
import com.mmunoz.meli.productdetail.impl.di.components.ProductDetailComponent
import com.mmunoz.meli.productdetail.wiring_impl.ProductDetailWiringModule
import com.mmunoz.meli.search.impl.di.components.SearchComponent
import com.mmunoz.meli.search.wiring_impl.SearchWiringModule
import com.mmunoz.meli.ui.activities.MainActivity
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import okhttp3.OkHttpClient
import javax.inject.Qualifier
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
    SearchComponent.Parent,
    ProductDetailComponent.Parent {

    fun inject(app: MainActivity)

    @WebServiceUrl
    fun getWebServiceUrl(): String

    fun getHttpClient(): OkHttpClient

    @Component.Builder
    interface Builder {
        fun build(): AppComponent

        @BindsInstance
        fun url(@WebServiceUrl storeType: String): Builder

        @BindsInstance
        fun application(application: MeLiApp): Builder
    }
}

@Qualifier
annotation class WebServiceUrl
