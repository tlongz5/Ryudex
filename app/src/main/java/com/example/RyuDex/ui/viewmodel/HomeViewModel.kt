package com.example.RyuDex.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.RyuDex.domain.usecase.GetBannerListUseCase
import com.example.RyuDex.domain.usecase.GetMangaCoverListFromQueryUseCase
import com.example.RyuDex.domain.usecase.GetMangaListFromTagUseCase
import com.example.RyuDex.model.MangaCover
import com.example.RyuDex.model.UiState
import com.example.RyuDex.utils.toMangaCover
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getMangaCoverListFromQueryUseCase: GetMangaCoverListFromQueryUseCase,
    private val getMangaListFromTagUseCase: GetMangaListFromTagUseCase,
    private val getBannerListUseCase: GetBannerListUseCase
) : ViewModel() {
    private val _mangaCoverShortList = MutableStateFlow<UiState<List<MangaCover>>>(UiState.Loading)
    val mangaCoverShortList = _mangaCoverShortList.asStateFlow()

    private val _mangaBannerList = MutableStateFlow<UiState<List<MangaCover>>>(UiState.Loading)
    val mangaBannerList = _mangaBannerList.asStateFlow()

    val getMangaCoverList: Flow<PagingData<MangaCover>> =
        getMangaCoverListFromQueryUseCase().cachedIn(viewModelScope)

    private val existList = mutableMapOf<String?, List<MangaCover>>()

    fun getMangaShortCoverList(tagId: String?) {
        viewModelScope.launch {
            _mangaCoverShortList.value = UiState.Loading
            val result = getList(tagId)
            result.onSuccess { mangaCovers ->
                _mangaCoverShortList.value = UiState.Success(mangaCovers)
            }.onFailure { exception ->
                _mangaCoverShortList.value = UiState.Error(exception.message?:"Unknown Error")
            }
        }
    }

    fun getMangaBannerList(){
        viewModelScope.launch {
            _mangaBannerList.value = UiState.Loading
            val result = getBannerListUseCase()
            result.onSuccess { mangaItems ->
                _mangaBannerList.value = UiState.Success(mangaItems.map { mangaItem -> mangaItem.toMangaCover() })
            }.onFailure { exception ->
                _mangaBannerList.value = UiState.Error(exception.message?:"Unknown Error")
            }
        }
    }
    private suspend fun getList(tagId:String?) : Result<List<MangaCover>>{
        if (existList.containsKey(tagId)) return Result.success(existList[tagId]?:emptyList())
        val result =  getMangaListFromTagUseCase(
            includedTags = if (tagId == null) null else listOf(tagId),
            limit = 15
        )
        return result.map { mangaItems ->
            val mangaCoverList =  mangaItems.map { mangaItem -> mangaItem.toMangaCover() }
            existList[tagId] = mangaCoverList
            mangaCoverList
        }
    }
}