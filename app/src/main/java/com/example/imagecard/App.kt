package com.example.imagecard

import android.app.Application
import com.example.imagecard.data.database.di.dbCardModule
import com.example.imagecard.di.cardViewModelModule
import com.example.imagecard.di.localDataSourceModule
import com.example.imagecard.di.repositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application(){
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(listOf(
                dbCardModule,
                localDataSourceModule,
                repositoryModule,
                cardViewModelModule
            ))
        }
    }
}