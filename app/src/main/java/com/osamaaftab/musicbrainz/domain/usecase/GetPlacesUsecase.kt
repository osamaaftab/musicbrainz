package com.osamaaftab.musicbrainz.domain.usecase

import com.osamaaftab.musicbrainz.domain.model.ApiResponse
import com.osamaaftab.musicbrainz.domain.model.RequestModel
import com.osamaaftab.musicbrainz.domain.repository.MusicRepository
import com.osamaaftab.musicbrainz.domain.usecase.base.UseCase
import com.osamaaftab.musicbrainz.util.handler.ApiErrorHandle

class GetPlacesUsecase constructor(
    private val musicRepository: MusicRepository,
    apiErrorHandle: ApiErrorHandle?
) :
    UseCase<ApiResponse, Any?>(apiErrorHandle) {
    override suspend fun run(requestModel: RequestModel): ApiResponse {
        return musicRepository.getPlaces(requestModel)
    }
}