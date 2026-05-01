package com.example.RyuDex.data.repo

import androidx.paging.PagingData
import com.example.RyuDex.model.ChapterImages
import com.example.RyuDex.model.MangaChapter
import com.example.RyuDex.model.MangaCover
import com.example.RyuDex.model.MangaItem
import kotlinx.coroutines.flow.Flow
import okhttp3.Response

interface MangaRepo {
    fun getMangaCoverListFromQuery(
        title: String? = null,
        authors: List<String>? = null,
        includedTags: List<String>? = null,
        orderFollowedCount: String? = null,
        orderCreatedAt: String? = null,
        orderYear: String? = null,
        includes: List<String>? = null
    ): Flow<PagingData<MangaCover>>

    suspend fun getMangaListFromTags(
        includedTags: List<String>? = null,
        limit:Int
    ): Result<List<MangaItem>>

    suspend fun getMangaBannerList() : Result<List<MangaItem>>

    suspend fun getMangaChapterList(id:String) : Result<List<MangaChapter>>

    suspend fun getMangaImages(id:String) : Result<ChapterImages>
}