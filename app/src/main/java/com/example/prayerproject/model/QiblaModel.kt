package com.example.prayerproject.model


import com.google.gson.annotations.SerializedName

data class QiblaModel(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("status")
    val status: String
)