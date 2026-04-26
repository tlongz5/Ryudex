package com.example.RyuDex.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.RyuDex.domain.usecase.GetMangaChapterListUseCase
import com.example.RyuDex.domain.usecase.GetMangaListFromTagUseCase
import com.example.RyuDex.model.MangaChapter
import com.example.RyuDex.model.MangaItem
import com.example.RyuDex.model.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getMangaChapterListUseCase: GetMangaChapterListUseCase,
    private val getMangaListFromTagsUseCase: GetMangaListFromTagUseCase
): ViewModel() {
    private val _mangaChaptersState = MutableStateFlow<UiState<List<MangaChapter>>>(UiState.Loading)
    val mangaChaptersState = _mangaChaptersState.asStateFlow()

    private val _relatedMangaState = MutableStateFlow<UiState<List<MangaItem>>>(UiState.Loading)
    val relatedMangaState = _relatedMangaState.asStateFlow()


    fun getMangaChapters(id:String){
        viewModelScope.launch {
            _mangaChaptersState.value = UiState.Loading
            val response = getMangaChapterListUseCase(id)
            response.onSuccess{ mangaChapters ->
                _mangaChaptersState.value = UiState.Success(mangaChapters)
            }.onFailure { exception ->
                _mangaChaptersState.value = UiState.Error(exception?.message?:"Unknown Error")
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
}