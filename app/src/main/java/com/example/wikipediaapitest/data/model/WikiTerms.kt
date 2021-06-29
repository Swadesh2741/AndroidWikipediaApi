package com.example.wikipediaapitest.data.model

import com.google.gson.annotations.SerializedName


data class WikiTerms(@SerializedName("description")
                     val description: List<String>?)