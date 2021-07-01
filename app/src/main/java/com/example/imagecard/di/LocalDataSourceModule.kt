package com.example.imagecard.di

import androidx.datastore.preferences.createDataStore
import com.example.imagecard.data.localdatasource.CardLocalDataSource
import com.example.imagecard.extention.PREFS_NAME
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val localDataSourceModule = module {
    single {
        androidContext().createDataStore(PREFS_NAME)
    }
    single {
        CardLocalDataSource(get(), get())
    }
}