package com.example.prayerproject.model


import com.google.gson.annotations.SerializedName

data class Meta(
    @SerializedName("latitude")
    val latitude: Double,
    @SerializedName("longitude")
    val longitude: Double,
    @SerializedName("method")
    val method: Method,
    @SerializedName("school")
    val school: String,
)