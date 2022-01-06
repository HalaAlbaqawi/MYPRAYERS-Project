package com.example.prayerproject.model


import com.google.gson.annotations.SerializedName

data class DuaaModel(
    @SerializedName("duaa")
    val duaa: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("userid")
    val userid: String = "",
    @SerializedName("isNotify")
    var isNotify: Boolean = false,
    @SerializedName("time")
    var time: String = "",
    @SerializedName("alarm")
    var alarm: Boolean = false


)