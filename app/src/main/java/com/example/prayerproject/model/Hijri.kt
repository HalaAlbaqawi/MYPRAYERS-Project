package com.example.prayerproject.model


import com.google.gson.annotations.SerializedName

data class Hijri(
    @SerializedName("date")
    val date: String,
    @SerializedName("day")
    val day: String,
    @SerializedName("format")
    val format: String,
    @SerializedName("year")
    val year: String
)