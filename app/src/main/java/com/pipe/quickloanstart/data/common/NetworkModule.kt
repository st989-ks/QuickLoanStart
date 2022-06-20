package com.pipe.quickloanstart.data.common

import com.pipe.quickloanstart.extensions.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(
        requestInterceptor: RequestInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder().apply {
            connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            addInterceptor(requestInterceptor)
        }.build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit
            .Builder().apply {
                baseUrl(BASE_URL)
                client(okHttpClient)
                addConverterFactory(ScalarsConverterFactory.create())
                addConverterFactory(GsonConverterFactory.create())
            }.build()
    }

    @Singleton
    @Provides
    fun provideInterceptor(
        sharedPrefs: SharedPrefs
    ): RequestInterceptor {
        return RequestInterceptor(sharedPrefs)
    }
}

