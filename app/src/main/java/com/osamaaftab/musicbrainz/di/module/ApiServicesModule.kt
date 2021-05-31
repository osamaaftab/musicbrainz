package com.osamaaftab.musicbrainz.di.module

import com.osamaaftab.musicbrainz.data.source.remote.MusicServices
import org.koin.dsl.module
import retrofit2.Retrofit

val ApiServicesModule = module {
    single { provideMusicService(get()) }
}

fun provideMusicService(retrofit: Retrofit): MusicServices {
    return retrofit.create(MusicServices::class.java)
}