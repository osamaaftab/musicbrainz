package com.osamaaftab.musicbrainz.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName

import kotlinx.android.parcel.Parcelize

@Parcelize
data class PlaceModel(
        val id: String?,
        val type: String?,
        val score: Int?,
        val name: String?,
        val address: String?,
        val coordinates: CoordinatesModel?,
        @SerializedName("life-span")
        val lifeSpan : LifeSpanModel?
) : Parcelable