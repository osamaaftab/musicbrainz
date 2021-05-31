package com.osamaaftab.musicbrainz.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ApiResponse(val places: List<PlaceModel>,val count:Int) : Parcelable