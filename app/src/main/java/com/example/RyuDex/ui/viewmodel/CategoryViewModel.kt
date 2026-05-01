package com.example.RyuDex.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.RyuDex.domain.usecase.GetMangaCoverListFromQueryUseCase
import com.example.RyuDex.model.MangaCover
import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.lifecycle.viewModelScope
import com.example.RyuDex.utils.Constant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val getMangaCoverListFromQueryUseCase: GetMangaCoverListFromQueryUseCase
): ViewModel() {
    private val cache = mutableMapOf<Pair<String?, String>, Flow<PagingData<MangaCover>>>()

    fun getMangaListFromTag(tagId: String?, filter:String): Flow<PagingData<MangaCover>> {
        val orderFollowedCount = if(filter == "Popular") "desc" else null
        val orderYear = if(filter == "Newest") "desc" else null
        val orderCreatedAt = if(filter == "Recently Added") "desc" else null

        val key = tagId to filter

        return cache.getOrPut(key) {
            getMangaCoverListFromQueryUseCase(
                includedTags = tagId?.let { listOf(it) },
                orderFollowedCount = orderFollowedCount,
                orderCreatedAt = orderCreatedAt,
                orderYear = orderYear
                )
                .cachedIn( viewModelScope)
        }
    }

}