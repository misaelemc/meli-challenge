package com.mmunoz.meli.categories.impl.di.modules

import androidx.lifecycle.ViewModel
import com.mmunoz.base.data.managers.DisposableManager
import com.mmunoz.base.di.scopes.FragmentScope
import com.mmunoz.base.di.scopes.ViewModelKey
import com.mmunoz.meli.categories.impl.data.models.CategoryModel
import com.mmunoz.meli.categories.impl.data.repositories.CategoriesRepository
import com.mmunoz.meli.categories.impl.ui.fragments.CategoriesFragment
import com.mmunoz.meli.categories.impl.ui.fragments.CategoriesNavigationFragment
import com.mmunoz.meli.categories.impl.ui.fragments.SubCategoriesFragment
import com.mmunoz.meli.categories.impl.ui.fragments.SubCategoriesFragment.Companion.SUB_CATEGORIES_ARGS
import com.mmunoz.meli.categories.impl.ui.viewModels.CategoriesViewModel
import com.mmunoz.meli.categories.impl.ui.viewModels.SubCategoriesViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

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

    @Binds
    @IntoMap
    @ViewModelKey(CategoriesViewModel::class)
    abstract fun bindCategoriesViewModel(viewModel: CategoriesViewModel): ViewModel
}

@Module
abstract class SubCategoriesFeatureModule {

    @ContributesAndroidInjector(modules = [SubCategoriesProviderModule::class])
    abstract fun bindSubCategoriesFragment(): SubCategoriesFragment
}

@Module
object SubCategoriesProviderModule {

    @IntoMap
    @Provides
    @ViewModelKey(SubCategoriesViewModel::class)
    fun provideViewModel(
        fragment: SubCategoriesFragment,
        repository: CategoriesRepository,
        disposableManager: DisposableManager
    ): ViewModel {
        val args = fragment.requireArguments().getParcelable<CategoryModel>(SUB_CATEGORIES_ARGS)
        return SubCategoriesViewModel(args!!, repository, disposableManager)
    }
}