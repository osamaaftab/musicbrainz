package com.osamaaftab.musicbrainz.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CoordinatesModel(
    val latitude: String?,
    val longitude: String?
) :
    Parcelable