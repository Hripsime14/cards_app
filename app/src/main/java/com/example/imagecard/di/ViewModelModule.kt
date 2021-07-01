package com.example.imagecard.di

import com.example.imagecard.ui.viewmodel.CardViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val cardViewModelModule = module {
    viewModel {
        CardViewModel(get())
    }
}