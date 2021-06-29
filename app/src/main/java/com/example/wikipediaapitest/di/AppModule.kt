package com.example.roomdatabasewithpaging3.di

import android.app.Application
import androidx.room.Room
import com.example.wikipediaapitest.adapter.WikiListAdapter
import com.example.wikipediaapitest.data.database.Database
import com.example.wikipediaapitest.network.ApiService
import com.example.wikipediaapitest.util.ItemTypeAdapterFactory
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun providesDatabase(context:Application) =
        Room.databaseBuilder(context, Database::class.java,"WikiDatabase")
            .build()

    @Provides
    @Singleton
    fun providesDao(database: Database) =
        database.getWikiPagesDao()


    @Provides
    fun provideWikiListAdapter(): WikiListAdapter = WikiListAdapter()


    @Provides
    @Singleton
    fun gson() = GsonBuilder()
        .setLenient()
        .registerTypeAdapterFactory(ItemTypeAdapterFactory)
        .create()!!


    @Provides
    @Singleton
    fun providesRetrofit() =
        Retrofit.Builder()
            .baseUrl(ApiService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson()))
            .build()
            .create(ApiService::class.java)!!

}