package com.example.wikipediaapitest.data.repository

import androidx.room.withTransaction
import com.example.wikipediaapitest.data.database.Database
import com.example.wikipediaapitest.data.model.WikiPages
import com.example.wikipediaapitest.network.ApiService
import com.example.wikipediaapitest.util.networkBoundResource
import javax.inject.Inject

class WikiPagesRepository @Inject constructor(
    private val api: ApiService,
    private val db: Database,
) {
    private val dao = db.getWikiPagesDao()

    fun getPages(title: String = "Sachin") = networkBoundResource(
        query = {
            dao.getAllPages()
        },
        fetch = {
            api.getPages(title)
        },
        saveFetchResult = { wikiPages ->
            db.withTransaction {
                dao.clearAll()
                dao.insertAll(wikiPages)
            }
        }
    )

    fun getAllOfflineData() = dao.getAllPages()

}
