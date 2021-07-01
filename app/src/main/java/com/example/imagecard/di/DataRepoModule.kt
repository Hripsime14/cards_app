package com.example.imagecard.di

import com.example.imagecard.data.datarepo.CardRepository
import org.koin.dsl.module

val repositoryModule = module {
    single {
        CardRepository(get())
    }
}