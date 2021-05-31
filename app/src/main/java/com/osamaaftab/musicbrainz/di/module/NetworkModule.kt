package com.osamaaftab.musicbrainz.di.module

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.osamaaftab.musicbrainz.MainApp
import com.osamaaftab.musicbrainz.util.interceptor.HeaderInterceptor
import retrofit2.converter.gson.GsonConverterFactory


val NetWorkModule = module {

    single { providesRetrofit(get(), get()) }
    single { providesOkHttpClient() }
    single { createGsonConverterFactory() }
}


fun providesRetrofit(
    okHttpClient: OkHttpClient, gsonConverterFactory: GsonConverterFactory
): Retrofit {
    return Retrofit.Builder()
        .baseUrl(MainApp().getApiUrl())
        .client(okHttpClient)
        .addConverterFactory(gsonConverterFactory)
        .addCallAdapterFactory(CoroutineCallAdapterFactory()).build()
}

fun createGsonConverterFactory(): GsonConverterFactory {
    return GsonConverterFactory.create()
}

fun providesOkHttpClient(): OkHttpClient {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
    return OkHttpClient.Builder()
        .connectTimeout(60L, TimeUnit.SECONDS)
        .readTimeout(60L, TimeUnit.SECONDS)
        .addInterceptor(httpLoggingInterceptor)
        .addInterceptor(HeaderInterceptor())
        .build()
}