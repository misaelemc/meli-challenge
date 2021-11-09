package com.mmunoz.base.di.modules

import androidx.lifecycle.ViewModelProvider
import com.mmunoz.base.di.factories.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class FactoryModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}