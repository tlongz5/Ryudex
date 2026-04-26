package com.example.RyuDex.domain.repoimpl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.RyuDex.data.remote.MangaApi
import com.example.RyuDex.data.remote.MangaPagingSource
import com.example.RyuDex.data.repo.MangaRepo
import com.example.RyuDex.model.ChapterImages
import com.example.RyuDex.model.MangaChapter
import com.example.RyuDex.model.MangaCover
import com.example.RyuDex.model.MangaItem
import com.example.RyuDex.utils.Constant
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MangaRepoImpl @Inject constructor(private val mangaApi: MangaApi) : MangaRepo{
    override fun getMangaCoverListFromQuery(
        title: String? ,
        authors: List<String>?,
        includedTags: List<String>?,
        order: String?,
        includes: List<String>?
    ): Flow<PagingData<MangaCover>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory =  { MangaPagingSource(
                mangaApi = mangaApi,
                title = title,
                authors = authors,
                includedTags = includedTags,
                order = order,
                includes = includes
            ) }
        ).flow
    }

    override suspend fun getMangaListFromTags(
        includedTags: List<String>?,
        limit:Int): Result<List<MangaItem>> {
        return runCatching {
            val response = mangaApi.getMangaListFromQuery(
                limit = limit,
                offset = (0..1000).random(),
                includedTags = includedTags,
                includes = Constant.requires
            )
            if (response.isSuccessful) {
                response.body()?.data ?: emptyList()
            } else {
                throw Exception(response.errorBody()?.string()?:"Error Connect, Try Again Later")
            }
        }
    }

    override suspend fun getMangaBannerList(): Result<List<MangaItem>> {
        return runCatching {
            val response = mangaApi.getMangaListFromQuery(
                limit = 10,
                offset = (0..1000).random(),
                order = "desc",
                includes = Constant.requires
            )

            if(response.isSuccessful){
                response.body()?.data?:emptyList()
            }else throw Exception(response.errorBody()?.string()?:"Error Connect, Try Again Later")
        }
    }

    override suspend fun getMangaChapterList(id:String): Result<List<MangaChapter>> {
        return runCatching {
            val response = mangaApi.getMangaChapterList(
                mangaId = id,
                limit = 500,
                offset = 0,
            )

            if(response.isSuccessful){
                response.body()?.data?:emptyList()
            }else throw Exception(response.errorBody()?.string()?:"Error Connect, Try Again Later")
        }
    }

    override suspend fun getMangaImages(chapterId:String): Result<ChapterImages> {
        return runCatching {
            val response = mangaApi.getChapterImages(chapterId)
            if(response.isSuccessful){
                response.body()!!
            }else throw Exception(response.errorBody()?.string()?:"Error Connect, Try Again Later")
        }
    }

}