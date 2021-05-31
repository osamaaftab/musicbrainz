package com.osamaaftab.musicbrainz.util.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val requestBuilder = originalRequest.newBuilder()
            .header("User-Agent", "Android")
        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}