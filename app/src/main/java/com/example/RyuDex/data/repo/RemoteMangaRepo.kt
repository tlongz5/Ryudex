package com.example.RyuDex.data.repo

import androidx.paging.PagingData
import com.example.RyuDex.model.dto.image.ChapterImagesDTO
import com.example.RyuDex.model.dto.chapter.MangaChapterDTO
import com.example.RyuDex.model.MangaCover
import com.example.RyuDex.model.dto.manga.MangaItemDTO
import com.example.RyuDex.model.dto.manga.TagItemDTO
import kotlinx.coroutines.flow.Flow

interface RemoteMangaRepo {
    fun getMangaCoverListFromQuery(
        title: String? = null,
        authors: List<String>? = null,
        includedTags: List<String>? = null,
        orderFollowedCount: String? = null,
        orderCreatedAt: String? = null,
        orderYear: String? = null,
        status:List<String>? = null,
        contentRating:List<String>? = null,
        availableTranslatedLanguage:List<String>? = null,
        includes: List<String>? = null
    ): Flow<PagingData<MangaCover>>

    suspend fun getMangaListFromTags(
        includedTags: List<String>? = null,
        limit:Int
    ): Result<List<MangaItemDTO>>

    suspend fun getMangaBannerList() : Result<List<MangaItemDTO>>

    suspend fun getMangaChapterList(id:String) : Result<List<MangaChapterDTO>>

    suspend fun getMangaImages(id:String) : Result<ChapterImagesDTO>

    suspend fun getTags(): Result<List<TagItemDTO>>

}