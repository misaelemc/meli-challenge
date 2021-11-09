package com.mmunoz.meli.categories.wiring_impl

import com.mmunoz.meli.categories.api.CategoriesFeatureLoader
import com.mmunoz.meli.categories.impl.ui.impls.CategoriesFeatureLoaderImpl
import dagger.Binds
import dagger.Module

@Module
abstract class CategoriesWiringModule {

    @Binds
    abstract fun bindCategoriesFragmentLoader(
        impl: CategoriesFeatureLoaderImpl
    ): CategoriesFeatureLoader
}