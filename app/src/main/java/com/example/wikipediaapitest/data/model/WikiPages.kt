package com.example.wikipediaapitest.data.model

import androidx.room.*
import com.google.gson.annotations.SerializedName
import java.io.Serializable
@Entity(tableName = "wikiPages")
data class WikiPages(
    @PrimaryKey
    @SerializedName("pageid")
    val pageid: Int,

    @SerializedName("title")
    val title: String?,

    @SerializedName("terms")
    val terms: WikiTerms?,

    @SerializedName("thumbnail")
    val thumbnail: WikiThumbnail?

) : Serializable