package com.example.wikipediaapitest.network

import com.example.wikipediaapitest.data.model.WikiPages
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    companion object{
        const val BASE_URL = "https://en.wikipedia.org//w/"
    }

   /* @GET("api.php?action=query&format=json&prop=pageimages%7Cpageterms&generator=prefixsearch&redirects=1&formatversion=2&piprop=thumbnail&pithumbsize=50&pilimit=10&wbptterms=description&gpslimit=30")
    suspend fun getPages(
        @Query("gpssearch") title:String?,
    ) : Response<ApiResponse>*/

    @GET("api.php?action=query&format=json&prop=pageimages%7Cpageterms&generator=prefixsearch&redirects=1&formatversion=2&piprop=thumbnail&pithumbsize=50&pilimit=10&wbptterms=description&gpslimit=30")
    suspend fun getPages(
        @Query("gpssearch") title:String?,
    ) : List<WikiPages>
}