package com.example.wikipediaapitest.data

import android.text.TextUtils
import androidx.room.TypeConverter
import com.example.wikipediaapitest.data.model.WikiTerms
import com.example.wikipediaapitest.data.model.WikiThumbnail
import com.google.gson.Gson

import com.google.gson.reflect.TypeToken

class DataConverter {

    @TypeConverter
    fun toWikiTerms(value: String?): WikiTerms? {
        if(TextUtils.isEmpty(value))
            return null
       else
            return Gson().fromJson(
                value,
                object : TypeToken<WikiTerms?>() {}.type
            )
    }

    @TypeConverter
    fun fromWikiTerms(data: WikiTerms?): String {
        return Gson().toJson(data)

    }

    @TypeConverter
    fun toWikiThumbnail(value: String?): WikiThumbnail? {

        if(TextUtils.isEmpty(value))
            return null
        else
           return Gson().fromJson(
               value,
               object : TypeToken<WikiThumbnail?>() {}.type
           )
    }

    @TypeConverter
    fun fromWikiThumbnail(data: WikiThumbnail?): String {
        return Gson().toJson(data)

    }
    @TypeConverter
    fun listToJson(value: List<String>?) = value?.let{Gson().toJson(value)}

    @TypeConverter
    fun jsonToList(value: String?) = value?.let{Gson().fromJson(value, Array<String>::class.java).toList()}


}