package com.example.prayerproject.model


import com.google.gson.annotations.SerializedName

data class PrayerModel(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val `data`: PrayersModel,
    @SerializedName("status")
    val status: String
)