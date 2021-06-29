package com.example.wikipediaapitest.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.roomdatabasewithpaging3.Data.Dao.WikiPagesDao
import com.example.wikipediaapitest.data.DataConverter
import com.example.wikipediaapitest.data.model.WikiPages


@Database(
    entities = [WikiPages::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DataConverter::class)
abstract class Database : RoomDatabase(){

    abstract fun getWikiPagesDao(): WikiPagesDao

}