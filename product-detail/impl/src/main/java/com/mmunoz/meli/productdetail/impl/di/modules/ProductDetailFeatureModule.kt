package com.mmunoz.meli.productdetail.impl.di.modules

import com.mmunoz.base.di.scopes.FragmentScope
import com.mmunoz.meli.productdetail.impl.ui.fragments.ProductDetailFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ProductDetailFeatureModule {

    @FragmentScope
    @ContributesAndroidInjector()
    abstract fun bindFragment(): ProductDetailFragment
}