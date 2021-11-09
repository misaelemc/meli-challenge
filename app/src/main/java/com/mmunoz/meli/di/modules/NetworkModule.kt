package com.mmunoz.meli.di.modules

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.Reusable
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
object NetworkModule {

    private const val PATH = "https://api.mercadolibre.com"
    private const val DEFAULT_TIME_OUT: Long = 5

    @Provides
    @Reusable
    fun gson(): Gson = GsonBuilder().create()

    @Provides
    @Singleton
    fun getOkHttpTimeout(): OkHttpClient.Builder {
        return OkHttpClient.Builder().apply {
            readTimeout(DEFAULT_TIME_OUT, TimeUnit.MINUTES)
            connectTimeout(DEFAULT_TIME_OUT, TimeUnit.MINUTES)
        }
    }

    @Provides
    @Singleton
    fun getServicesClient(httpClient: OkHttpClient.Builder): Retrofit {
        return Retrofit.Builder()
            .client(httpClient.build())
            .baseUrl(PATH)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }
}