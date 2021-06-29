package com.example.wikipediaapitest.data.model


import com.google.gson.annotations.SerializedName


data class WikiThumbnail(
    @SerializedName("source")
    val source: String?
)