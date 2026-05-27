package com.example.RyuDex.data.remote

import com.example.RyuDex.model.ApiResponse
import com.example.RyuDex.model.dto.image.ChapterImagesDTO
import com.example.RyuDex.model.dto.chapter.MangaChapterDTO
import com.example.RyuDex.model.dto.manga.MangaItemDTO
import com.example.RyuDex.model.dto.manga.TagItemDTO
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

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
        @Query("status[]") status:List<String>? = null,
        @Query("contentRating[]") contentRating:List<String>? = null,
        @Query("availableTranslatedLanguage[]") availableTranslatedLanguage:List<String>? = null,
        @Query("includes[]") includes: List<String>? = null
    ) : Response<ApiResponse<MangaItemDTO>>

    @GET("manga/{id}/feed")
    suspend fun getMangaChapterList(
        @Path("id") mangaId:String,
        @Query("limit") limit:Int,
        @Query("offset") offset:Int,
        @Query("order[chapter]") order: String = "asc"
    ) : Response<ApiResponse<MangaChapterDTO>>

    @GET("at-home/server/{chapterId}")
    suspend fun getChapterImages(
        @Path("chapterId") chapterId:String
    ) : Response<ChapterImagesDTO>

    @GET("manga/tag")
    suspend fun getTags(): Response<ApiResponse<TagItemDTO>>

    @GET
    suspend fun downloadImage(
        @Url imgUrl: String
    ): Response<ResponseBody>
}