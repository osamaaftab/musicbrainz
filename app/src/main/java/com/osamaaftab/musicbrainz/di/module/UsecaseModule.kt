package com.osamaaftab.musicbrainz.di.module

import com.osamaaftab.musicbrainz.domain.repository.MusicRepository
import com.osamaaftab.musicbrainz.domain.usecase.GetPlacesUsecase
import com.osamaaftab.musicbrainz.util.handler.ApiErrorHandle
import org.koin.dsl.module

val UseCaseModule = module {
    single { provideGetPlacesUsecase(get(), provideApiError()) }
}

fun provideGetPlacesUsecase(
    musicRepository: MusicRepository,
    apiErrorHandle: ApiErrorHandle
): GetPlacesUsecase {
    return GetPlacesUsecase(musicRepository, apiErrorHandle)
}

