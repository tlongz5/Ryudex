package com.example.RyuDex.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.RyuDex.domain.usecase.CancelDownloadMangaUseCase
import com.example.RyuDex.domain.usecase.GetMangaChapterListUseCase
import com.example.RyuDex.domain.usecase.GetMangaListFromTagUseCase
import com.example.RyuDex.domain.usecase.RequestDownloadMangaUseCase
import com.example.RyuDex.model.MangaCover
import com.example.RyuDex.model.dto.chapter.MangaChapterDTO
import com.example.RyuDex.model.dto.manga.MangaItemDTO
import com.example.RyuDex.model.UiState
import com.example.RyuDex.model.entity.MangaChapterEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getMangaChapterListUseCase: GetMangaChapterListUseCase,
    private val getMangaListFromTagsUseCase: GetMangaListFromTagUseCase,
    private val requestDownloadMangaUseCase: RequestDownloadMangaUseCase
): ViewModel() {
    private val _mangaChaptersStateDTO = MutableStateFlow<UiState<List<MangaChapterDTO>>>(UiState.Loading)
    val mangaChaptersState = _mangaChaptersStateDTO.asStateFlow()

    private val _relatedMangaState = MutableStateFlow<UiState<List<MangaItemDTO>>>(UiState.Loading)
    val relatedMangaState = _relatedMangaState.asStateFlow()


    fun getMangaChapters(id:String){
        viewModelScope.launch {
            _mangaChaptersStateDTO.value = UiState.Loading
            val response = getMangaChapterListUseCase(id)
            response.onSuccess{ mangaChapters ->
                _mangaChaptersStateDTO.value = UiState.Success(mangaChapters)
            }.onFailure { exception ->
                _mangaChaptersStateDTO.value = UiState.Error(exception?.message?:"Unknown Error")
            }
        }
    }

    fun getRelatedManga(tags: List<String>){
        viewModelScope.launch {
            _relatedMangaState.value = UiState.Loading
            val response = getMangaListFromTagsUseCase(tags,15)
            response.onSuccess{ mangaChapters ->
                _relatedMangaState.value = UiState.Success(mangaChapters)
            }.onFailure { exception ->
                _relatedMangaState.value = UiState.Error(exception?.message ?: "Unknown Error")
            }
        }
    }

    fun requestDownloadManga(mangaCover: MangaCover, mangaToDownload: List<MangaChapterDTO>) {
        viewModelScope.launch {
            requestDownloadMangaUseCase(mangaCover, mangaToDownload)
        }
    }

}