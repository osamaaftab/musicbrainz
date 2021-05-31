package com.osamaaftab.musicbrainz.di.module

import com.osamaaftab.musicbrainz.util.handler.ApiErrorHandle
import org.koin.dsl.module

val AppModule = module {
    single { provideApiError() }
}

fun provideApiError(): ApiErrorHandle {
    return ApiErrorHandle()
}