package com.example.imagecard.data.database.di

import androidx.room.Room
import com.example.imagecard.constants.DATABASE_NAME
import com.example.imagecard.data.database.CardDatabase
import com.example.imagecard.data.database.dao.CardDAO
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val dbCardModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            CardDatabase::class.java,
            DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    fun provideCardDao(database: CardDatabase): CardDAO = database.cardDao()

    single {
        provideCardDao(get())
    }
}