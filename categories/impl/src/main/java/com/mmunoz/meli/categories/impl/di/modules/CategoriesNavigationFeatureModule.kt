package com.mmunoz.meli.categories.impl.di.modules

import com.mmunoz.base.di.scopes.FragmentScope
import com.mmunoz.meli.categories.impl.ui.fragments.CategoriesFragment
import com.mmunoz.meli.categories.impl.ui.fragments.CategoriesNavigationFragment
import com.mmunoz.meli.categories.impl.ui.fragments.SubCategoriesFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class CategoriesNavigationFeatureModule {

    @FragmentScope
    @ContributesAndroidInjector(
        modules = [
            CategoriesFeatureModule::class,
            SubCategoriesFeatureModule::class
        ]
    )
    abstract fun bindCategoriesNavigationFragment(): CategoriesNavigationFragment
}

@Module
abstract class CategoriesFeatureModule {


    @ContributesAndroidInjector()
    abstract fun bindCategoriesFragment(): CategoriesFragment
}

@Module
abstract class SubCategoriesFeatureModule {

    @ContributesAndroidInjector()
    abstract fun bindSubCategoriesFragment(): SubCategoriesFragment
}