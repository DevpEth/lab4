package com.codepath.campgrounds

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

// Response wrapper from API
@Serializable
data class CampgroundResponse(
    @SerialName("data")
    val data: List<Campground>
)

// Campground data class
@Parcelize
@Serializable
data class Campground(
    @SerialName("name")
    val name: String? = null,
    @SerialName("description")
    val description: String? = null,
    @SerialName("latitude")
    val latitude: String? = null,
    @SerialName("longitude")
    val longitude: String? = null,
    @SerialName("images")
    val images: List<CampgroundImage>? = emptyList()
) : Parcelable

@Parcelize
@Serializable
data class CampgroundImage(
    @SerialName("url")
    val url: String? = null
) : Parcelable
