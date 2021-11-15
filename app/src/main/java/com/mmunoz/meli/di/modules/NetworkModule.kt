package com.mmunoz.meli.di.modules

import androidx.test.espresso.idling.CountingIdlingResource
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.mmunoz.meli.BuildConfig
import com.mmunoz.meli.di.components.WebServiceUrl
import dagger.Module
import dagger.Provides
import dagger.Reusable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
object NetworkModule {

    private const val DEFAULT_TIME_OUT: Long = 5

    @Provides
    @Reusable
    fun gson(): Gson = GsonBuilder().create()

    @Provides
    @Singleton
    fun getOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().apply {
            readTimeout(DEFAULT_TIME_OUT, TimeUnit.MINUTES)
            connectTimeout(DEFAULT_TIME_OUT, TimeUnit.MINUTES)

            val httpLoggingInterceptor =
                HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT)
            if (BuildConfig.DEBUG) {
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                addInterceptor(httpLoggingInterceptor)
            }
        }.build()
    }

    @Provides
    @Singleton
    fun getServicesClient(httpClient: OkHttpClient, @WebServiceUrl url: String): Retrofit {
        return Retrofit.Builder()
            .client(httpClient)
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun getIdLingResource(): CountingIdlingResource {
        return CountingIdlingResource("MeLi")
    }
}