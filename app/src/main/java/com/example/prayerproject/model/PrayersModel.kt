package com.example.prayerproject.model


import com.google.gson.annotations.SerializedName

data class PrayersModel(
    @SerializedName("date")
    val date: Date,
    @SerializedName("meta")
    val meta: Meta,
    @SerializedName("timings")
    val timings: Timings
)