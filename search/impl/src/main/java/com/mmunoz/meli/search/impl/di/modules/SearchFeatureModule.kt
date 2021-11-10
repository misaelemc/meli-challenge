package com.mmunoz.meli.search.impl.di.modules

import com.mmunoz.base.di.scopes.FragmentScope
import com.mmunoz.meli.search.impl.ui.fragments.SearchBottomSheet
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class SearchFeatureModule {

    @FragmentScope
    @ContributesAndroidInjector()
    abstract fun bindFragment(): SearchBottomSheet
}