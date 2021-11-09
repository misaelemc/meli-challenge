package com.mmunoz.meli.productdetail.wiring_impl

import com.mmunoz.meli.productdetail.api.ProductDetailFeatureLoader
import com.mmunoz.meli.productdetail.impl.ui.impls.ProductDetailFeatureLoaderImpl
import dagger.Binds
import dagger.Module

@Module
abstract class ProductDetailWiringModule {

    @Binds
    abstract fun bindProductDetailFragmentLoader(
        impl: ProductDetailFeatureLoaderImpl
    ): ProductDetailFeatureLoader
}