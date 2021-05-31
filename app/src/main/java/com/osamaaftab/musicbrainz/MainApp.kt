package com.osamaaftab.musicbrainz

import android.app.Application
import com.osamaaftab.musicbrainz.di.module.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

open class MainApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MainApp)
            modules(
                listOf(
                    AppModule, ApiServicesModule, NetWorkModule,
                    RepositoryModule, UseCaseModule, ViewModelModule
                )
            )
        }
    }

    open fun getApiUrl(): String {
        return "https://musicbrainz.org"
    }
}