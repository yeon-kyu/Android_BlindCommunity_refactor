package com.yeonkyu.blindcommunity2.di

import android.app.Application
import androidx.room.Room
import com.yeonkyu.blindcommunity2.data.room_persistence.FavoritesDao
import com.yeonkyu.blindcommunity2.data.room_persistence.FavoritesDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val roomDBModule = module{

    fun provideRoomDatabase(application: Application): FavoritesDatabase =
        Room.databaseBuilder(application, FavoritesDatabase::class.java, "favorites-db")
            .build()

    fun provideFavoritesDao(database: FavoritesDatabase): FavoritesDao{
        return database.favoritesDao()
    }

    single { provideRoomDatabase(androidApplication()) }
    single { provideFavoritesDao(get()) }
}