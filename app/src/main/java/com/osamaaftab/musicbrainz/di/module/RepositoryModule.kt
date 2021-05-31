package com.osamaaftab.musicbrainz.di.module

import com.osamaaftab.musicbrainz.data.repository.MusicRepositoryImp
import com.osamaaftab.musicbrainz.data.source.remote.MusicServices
import com.osamaaftab.musicbrainz.domain.repository.MusicRepository
import org.koin.dsl.module

val RepositoryModule = module {

    single { provideMusicRepository(get()) }
}

fun provideMusicRepository(musicServices: MusicServices): MusicRepository {
    return MusicRepositoryImp(musicServices)
}