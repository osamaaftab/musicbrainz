package com.osamaaftab.musicbrainz.util

import android.net.Uri
import com.osamaaftab.musicbrainz.util.Util.Companion.asset
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest

class SuccessDispatcher() : Dispatcher() {

    private val responseFilesByPath: Map<String, String> = mapOf(
        APIPaths.LIST to MockFiles.LIST_SUCCESS
    )

    override fun dispatch(request: RecordedRequest): MockResponse {
        val errorResponse = MockResponse().setResponseCode(404)

        val pathWithoutQueryParams = Uri.parse(request?.path).path ?: return errorResponse
        val responseFile = responseFilesByPath[pathWithoutQueryParams]
        return if (responseFile != null) {
            val responseBody = asset(responseFile)
            MockResponse().setResponseCode(200).setBody(responseBody)
        } else {
            errorResponse
        }
    }
}