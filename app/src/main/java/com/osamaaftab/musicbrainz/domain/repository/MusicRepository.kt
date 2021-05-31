package com.osamaaftab.musicbrainz.domain.repository

import com.osamaaftab.musicbrainz.domain.model.ApiResponse
import com.osamaaftab.musicbrainz.domain.model.RequestModel

interface MusicRepository {
    suspend fun getPlaces(requestModel: RequestModel): ApiResponse
}