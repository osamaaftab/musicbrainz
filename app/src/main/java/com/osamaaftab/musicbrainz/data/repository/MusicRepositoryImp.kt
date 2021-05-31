package com.osamaaftab.musicbrainz.data.repository

import com.osamaaftab.musicbrainz.data.source.remote.MusicServices
import com.osamaaftab.musicbrainz.domain.model.ApiResponse
import com.osamaaftab.musicbrainz.domain.model.RequestModel
import com.osamaaftab.musicbrainz.domain.repository.MusicRepository

class MusicRepositoryImp(private val musicServices: MusicServices) : MusicRepository {
    override suspend fun getPlaces(requestModel: RequestModel): ApiResponse {
        return musicServices.getPlaces(requestModel.param as String, "json", requestModel.offset, requestModel.limit).await()
    }
}