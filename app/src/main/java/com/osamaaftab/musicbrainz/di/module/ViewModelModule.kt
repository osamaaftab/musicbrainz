package com.osamaaftab.musicbrainz.di.module

import com.osamaaftab.musicbrainz.presentation.viewmodel.ResultViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val ViewModelModule = module {
    viewModel { ResultViewModel(get()) }
}