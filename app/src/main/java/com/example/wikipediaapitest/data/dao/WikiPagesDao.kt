package com.example.roomdatabasewithpaging3.Data.Dao

import android.icu.text.CaseMap
import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.wikipediaapitest.data.model.WikiPages
import kotlinx.coroutines.flow.Flow

@Dao
interface WikiPagesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(pages: List<WikiPages>)

    @Query("SELECT * FROM wikiPages")
    fun getAllPages(): Flow<List<WikiPages>>

    @Query("SELECT * FROM wikiPages WHERE title = :title")
    fun getWikiPages(title: String): Flow<List<WikiPages>>


    @Query("DELETE FROM wikiPages")
    fun clearAll()

}