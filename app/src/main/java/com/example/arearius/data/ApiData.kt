package com.example.arearius.data

import com.google.gson.annotations.SerializedName
import java.util.*

data class ApiData(
    @SerializedName("date") val date: Date,
    @SerializedName("localName") val dateName: String,
    @SerializedName("name") val engName: String,
    @SerializedName("countryCode") val locale: String,
    @SerializedName("fixed") val fixed: Boolean,
    @SerializedName("global") val global: Boolean,
    @SerializedName("counties") val counties: String?,
    @SerializedName("launchYear") val launchYear: String?,
    @SerializedName("type") val type: String
)
