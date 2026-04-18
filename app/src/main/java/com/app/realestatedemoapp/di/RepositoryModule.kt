package com.app.realestatedemoapp.di

import com.app.realestatedemoapp.data.repository.PropertyRepositoryImpl
import com.app.realestatedemoapp.domain.PropertyRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun providePropertyRepository(propertyRepositoryImpl: PropertyRepositoryImpl): PropertyRepository
}