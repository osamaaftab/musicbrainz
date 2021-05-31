package com.osamaaftab.musicbrainz.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LifeSpanModel(
    val begin: String?,
    val end: String?,
    val ended: Boolean?

) : Parcelable