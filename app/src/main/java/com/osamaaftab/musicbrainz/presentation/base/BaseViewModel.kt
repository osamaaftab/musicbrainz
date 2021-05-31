package com.osamaaftab.musicbrainz.presentation.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel

abstract class BaseViewModel : ViewModel() {

    val apiPageLimit = 20
    val scope = CoroutineScope(
        Job() + Dispatchers.Main
    )

    // Cancel the job when the view model is destroyed
    override fun onCleared() {
        super.onCleared()
        scope.cancel()
    }
}