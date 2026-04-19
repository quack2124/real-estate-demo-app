package com.app.realestatedemoapp.di

import android.content.Context
import com.app.realestatedemoapp.data.remote.ApiService
import com.app.realestatedemoapp.domain.NetworkConnectivityObserver
import com.app.realestatedemoapp.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideApiService(): ApiService =
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ApiService::class.java)

    @Singleton
    @Provides
    fun provideConnectivityObserver(@ApplicationContext context: Context): NetworkConnectivityObserver {
        return NetworkConnectivityObserver(context)
    }
}