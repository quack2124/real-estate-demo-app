package com.app.realestatedemoapp.di

import android.content.Context
import androidx.room.Room
import com.app.realestatedemoapp.data.local.AppDatabase
import com.app.realestatedemoapp.data.local.dao.PropertyDao
import com.app.realestatedemoapp.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase = Room.databaseBuilder(
        context, AppDatabase::class.java, Constants.DB_NAME
    ).build()

    @Provides
    fun providePropertyDao(db: AppDatabase): PropertyDao = db.propertyDao()
}
