package com.mmunoz.meli.productdetail.impl.di.modules

import androidx.lifecycle.ViewModel
import com.mmunoz.base.di.scopes.FragmentScope
import com.mmunoz.base.di.scopes.ViewModelKey
import com.mmunoz.meli.productdetail.api.data.models.Product
import com.mmunoz.meli.productdetail.impl.ui.fragments.ProductDetailBottomSheet
import com.mmunoz.meli.productdetail.impl.ui.fragments.ProductDetailBottomSheet.Companion.PRODUCT_DETAIL_ARGS
import com.mmunoz.meli.productdetail.impl.ui.viewModels.ProductDetailViewModel
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class ProductDetailFeatureModule {

    @FragmentScope
    @ContributesAndroidInjector(modules = [ProductDetailProviderModule::class])
    abstract fun bindBottomSheet(): ProductDetailBottomSheet
}

@Module
object ProductDetailProviderModule {

    @IntoMap
    @Provides
    @ViewModelKey(ProductDetailViewModel::class)
    fun provideViewModel(
        bottomSheet: ProductDetailBottomSheet
    ): ViewModel {
        val product = bottomSheet.requireArguments().getParcelable<Product>(PRODUCT_DETAIL_ARGS)
        return ProductDetailViewModel(product!!)
    }
}