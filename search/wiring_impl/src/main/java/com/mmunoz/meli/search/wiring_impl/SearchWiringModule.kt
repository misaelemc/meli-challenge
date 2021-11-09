package com.mmunoz.meli.search.wiring_impl

import com.mmunoz.meli.search.impl.ui.impls.SearchFeatureLoaderImpl
import com.mmunoz.meli.search.api.SearchFeatureLoader
import dagger.Binds
import dagger.Module

@Module
abstract class SearchWiringModule {

    @Binds
    abstract fun bindSearchFeatureLoader(
        impl: SearchFeatureLoaderImpl
    ): SearchFeatureLoader
}