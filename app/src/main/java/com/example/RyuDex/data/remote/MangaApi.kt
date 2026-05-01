package com.example.RyuDex.data.remote

import com.example.RyuDex.model.ApiResponse
import com.example.RyuDex.model.ChapterImages
import com.example.RyuDex.model.MangaChapter
import com.example.RyuDex.model.MangaItem
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MangaApi {
    @GET("manga")
    suspend fun getMangaListFromQuery(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("title") title:String? = null,
        @Query("authors[]") authors:List<String>? = null,
        @Query("includedTags[]") includedTags:List<String>? = null,
        @Query("order[followedCount]") orderFollowedCount:String? = null,
        @Query("order[createdAt]") orderCreatedAt:String? = null,
        @Query("order[year]") orderYear:String? = null,
        @Query("includes[]") includes: List<String>? = null
    ) : Response<ApiResponse<MangaItem>>

    @GET("manga/{id}/feed")
    suspend fun getMangaChapterList(
        @Path("id") mangaId:String,
        @Query("limit") limit:Int,
        @Query("offset") offset:Int,
        @Query("order[chapter]") order: String = "asc"
    ) : Response<ApiResponse<MangaChapter>>

    @GET("at-home/server/{chapterId}")
    suspend fun getChapterImages(
        @Path("chapterId") chapterId:String
    ) : Response<ChapterImages>
}