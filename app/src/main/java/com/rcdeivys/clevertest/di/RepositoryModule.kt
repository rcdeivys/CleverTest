package com.rcdeivys.clevertest.di

import com.rcdeivys.clevertest.repositories.CleverRepository
import org.koin.dsl.module

val repositoryModule = module {
    factory {
        CleverRepository()
    }
}