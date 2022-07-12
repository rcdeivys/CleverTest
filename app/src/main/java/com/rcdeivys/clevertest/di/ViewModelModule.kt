package com.rcdeivys.clevertest.di

import com.rcdeivys.clevertest.ui.details.viewmodels.DetailsViewModel
import com.rcdeivys.clevertest.ui.home.viewmodels.HomeViewModel
import com.rcdeivys.clevertest.ui.locations.LocationsViewModel
import com.rcdeivys.clevertest.ui.locationsdetails.LocationsDetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { DetailsViewModel(get()) }
    viewModel { LocationsViewModel(get()) }
    viewModel { LocationsDetailsViewModel(get()) }
}