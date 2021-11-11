package com.mmunoz.meli.search.impl.di.modules

import androidx.lifecycle.ViewModel
import com.mmunoz.base.di.scopes.FragmentScope
import com.mmunoz.base.di.scopes.ViewModelKey
import com.mmunoz.meli.search.impl.data.api.SearchApi
import com.mmunoz.meli.search.impl.data.repositories.SearchRepository
import com.mmunoz.meli.search.impl.ui.fragments.SearchFragment
import com.mmunoz.meli.search.impl.ui.viewModels.SearchViewModel
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import retrofit2.Retrofit

@Module
abstract class SearchFeatureModule {

    @FragmentScope
    @ContributesAndroidInjector(modules = [SearchProviderModule::class])
    abstract fun bindFragment(): SearchFragment
}

@Module
object SearchProviderModule {

    @Provides
    fun provideSearchApi(retrofit: Retrofit): SearchApi {
        return retrofit.create(SearchApi::class.java)
    }

    @IntoMap
    @Provides
    @ViewModelKey(SearchViewModel::class)
    fun provideViewModel(
        repository: SearchRepository
    ): ViewModel {
        return SearchViewModel(repository)
    }
}