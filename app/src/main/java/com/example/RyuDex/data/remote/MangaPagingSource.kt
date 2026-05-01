package com.example.RyuDex.data.remote

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.RyuDex.model.ApiResponse
import com.example.RyuDex.model.MangaCover
import com.example.RyuDex.model.MangaItem
import com.example.RyuDex.utils.Constant
import com.example.RyuDex.utils.toMangaCover

class MangaPagingSource(
    private val mangaApi: MangaApi,
    private val title: String? = null,
    private val authors: List<String>? = null,
    private val includedTags: List<String>? = null,
    private val orderFollowedCount: String? = null,
    private val orderCreatedAt: String? = null,
    private val orderYear: String? = null,
    private val includes: List<String>? = null
): PagingSource<Int, MangaCover>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MangaCover> {
        return try {
            val offset = params.key?:0
            val limit = params.loadSize
            val response = mangaApi.getMangaListFromQuery(
                limit = limit,
                offset = offset,
                title = title,
                authors = authors,
                includedTags = includedTags,
                orderFollowedCount = orderFollowedCount,
                orderCreatedAt = orderCreatedAt,
                orderYear = orderYear,
                includes = Constant.requires // NOTEEEEEEEEEEEEEE
            )
            Log.d("Api","Get Api Success")

            if(response.isSuccessful && response.body() != null){
                val mangaList = response.body()!!
                val nextKey = if(offset +limit< mangaList.total) offset+limit else null
                LoadResult.Page(
                    data = mangaList.data.map { it.toMangaCover() },
                    prevKey = if (offset==0) null else offset-limit,
                    nextKey = nextKey
                )
            }else LoadResult.Error(Exception("Get Api Fail"))
        }catch (e:Exception){
            Log.e("Api", "Error loading: ${e.message}", e)
            LoadResult.Error(Exception("Get Api Fail"))
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MangaCover>): Int? {
        return null
    }
}