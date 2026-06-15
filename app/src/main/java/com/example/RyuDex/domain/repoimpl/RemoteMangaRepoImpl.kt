package com.example.RyuDex.domain.repoimpl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.RyuDex.data.remote.MangaApi
import com.example.RyuDex.data.remote.MangaPagingSource
import com.example.RyuDex.data.repo.RemoteMangaRepo
import com.example.RyuDex.model.dto.image.ChapterImagesDTO
import com.example.RyuDex.model.dto.chapter.MangaChapterDTO
import com.example.RyuDex.model.MangaCover
import com.example.RyuDex.model.dto.manga.MangaItemDTO
import com.example.RyuDex.model.dto.manga.TagItemDTO
import com.example.RyuDex.utils.Constant
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RemoteMangaRepoImpl @Inject constructor(private val mangaApi: MangaApi) : RemoteMangaRepo{
    override fun getMangaCoverListFromQuery(
        title: String? ,
        authors: List<String>?,
        includedTags: List<String>?,
        orderFollowedCount: String?,
        orderCreatedAt: String?,
        orderYear: String?,
        status:List<String>?,
        contentRating:List<String>?,
        availableTranslatedLanguage:List<String>?,
        includes: List<String>?
    ): Flow<PagingData<MangaCover>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory =  { MangaPagingSource(
                mangaApi = mangaApi,
                title = title,
                authors = authors,
                includedTags = includedTags,
                orderFollowedCount = orderFollowedCount,
                orderCreatedAt = orderCreatedAt,
                orderYear = orderYear,
                status = status,
                contentRating = contentRating,
                availableTranslatedLanguage = availableTranslatedLanguage,
                includes = includes
            ) }
        ).flow
    }

    override suspend fun getMangaListFromTags(
        includedTags: List<String>?,
        limit:Int): Result<List<MangaItemDTO>> {
        return runCatching {
            val response = mangaApi.getMangaListFromQuery(
                limit = limit,
                offset = (0..1000).random(),
                includedTags = includedTags,
                orderFollowedCount = "desc",
                includes = Constant.requires
            )
            if (response.isSuccessful) {
                response.body()?.data ?: emptyList()
            } else {
                throw Exception(response.errorBody()?.string()?:"Error Connect, Try Again Later")
            }
        }
    }

    override suspend fun getMangaBannerList(): Result<List<MangaItemDTO>> {
        return runCatching {
            val response = mangaApi.getMangaListFromQuery(
                limit = 10,
                offset = (0..1000).random(),
                orderFollowedCount = "desc",
                includes = Constant.requires
            )

            if(response.isSuccessful){
                response.body()?.data?:emptyList()
            }else throw Exception(response.errorBody()?.string()?:"Error Connect, Try Again Later")
        }
    }

    override suspend fun getMangaChapterList(id:String): Result<List<MangaChapterDTO>> {
        return runCatching {
            val list = mutableListOf<MangaChapterDTO>()
            val limit = 500
            for(i in 0..20){
                val response = mangaApi.getMangaChapterList(
                    mangaId = id,
                    limit = limit,
                    offset = i * 500, // 500 chapter per request
                )
                if(response.isSuccessful){
                    val data = response.body()?.data?: emptyList()
                    list+=data
                    if(data.size<limit) break
                }else throw Exception(response.errorBody()?.string()?:"Error Connect, Try Again Later")
            }
            list
        }
    }

    override suspend fun getMangaImages(chapterId:String): Result<ChapterImagesDTO> {
        return runCatching {
            val response = mangaApi.getChapterImages(chapterId)
            if(response.isSuccessful){
                response.body()!!
            }else throw Exception(response.errorBody()?.string()?:"Error Connect, Try Again Later")
        }
    }

    override suspend fun getTags(): Result<List<TagItemDTO>> {
        return runCatching {
            val response = mangaApi.getTags()
            if (response.isSuccessful){
                response.body()?.data?:emptyList()
            }else throw Exception(response.errorBody()?.string()?:"Error Connect, Try Again Later")
        }
    }

}