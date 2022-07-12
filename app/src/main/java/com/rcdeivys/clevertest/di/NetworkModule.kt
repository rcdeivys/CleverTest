package com.rcdeivys.clevertest.di

import com.rcdeivys.clevertest.api.ApiService
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val URL_BASE = "https://rickandmortyapi.com/api/"

val networkModule = module {
    single { provideOkHttpClient() }
    single { provideRetrofit(get()) }
    single { provideApiService(get(), ApiService::class.java) }
}

fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder().build()

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
    .baseUrl(URL_BASE)
    .client(okHttpClient)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

fun provideApiService(retrofit: Retrofit, apiService: Class<ApiService>) =
    createService(retrofit, apiService)

fun <T> createService(retrofit: Retrofit, serviceClass: Class<T>): T = retrofit.create(serviceClass)