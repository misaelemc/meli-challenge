package com.mmunoz.meli.categories.impl.di.modules

import com.mmunoz.meli.categories.impl.data.api.CategoriesApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
object CategoriesNetworkModule {

    @Provides
    fun provideCategoriesApi(retrofit: Retrofit): CategoriesApi {
        return retrofit.create(CategoriesApi::class.java)
    }
}