package com.osamaaftab.musicbrainz.domain.model

data class RequestModel(
    val offset: Int,
    val limit: Int,
    val param: Any?,
)