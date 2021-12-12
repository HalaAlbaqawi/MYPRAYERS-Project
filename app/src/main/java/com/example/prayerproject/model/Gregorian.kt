package com.example.prayerproject.model


import com.google.gson.annotations.SerializedName

data class Gregorian(
    @SerializedName("date")
    val date: String,
    @SerializedName("day")
    val day: String,
    @SerializedName("format")
    val format: String,
    @SerializedName("month")
    val month: Month,
    @SerializedName("year")
    val year: String
)