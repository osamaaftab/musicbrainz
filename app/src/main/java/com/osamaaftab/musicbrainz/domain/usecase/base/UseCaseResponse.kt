package com.osamaaftab.musicbrainz.domain.usecase.base

import com.osamaaftab.musicbrainz.domain.model.base.ErrorModel


interface UseCaseResponse<in Type> {

    fun onSuccess(result: Type)

    fun onError(errorModel: ErrorModel?)
}