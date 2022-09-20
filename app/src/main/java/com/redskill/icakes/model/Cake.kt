package com.redskill.icakes.model


import com.google.gson.annotations.SerializedName

data class Cake(
    @SerializedName("desc")
    val desc: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("title")
    val title: String
)