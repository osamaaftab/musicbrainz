package com.osamaaftab.musicbrainz.data.source.remote

import com.osamaaftab.musicbrainz.domain.model.ApiResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface MusicServices {
    @GET("/ws/2/place")
    fun getPlaces(
        @Query("query") query: String,
        @Query("fmt") type: String,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int): Deferred<ApiResponse>
}