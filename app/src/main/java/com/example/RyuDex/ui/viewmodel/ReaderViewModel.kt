package com.example.RyuDex.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.RyuDex.domain.usecase.GetMangaChapterListUseCase
import com.example.RyuDex.domain.usecase.GetMangaImagesUseCase
import com.example.RyuDex.model.MangaPage
import com.example.RyuDex.model.UiState
import com.example.RyuDex.model.dto.chapter.MangaChapterDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReaderViewModel @Inject constructor(
    private val getChapterImagesUseCase: GetMangaImagesUseCase,
    private val getMangaChapterListUseCase: GetMangaChapterListUseCase
) : ViewModel() {
    private val _mangaChaptersStateDTO = MutableStateFlow<UiState<List<MangaChapterDTO>>>(UiState.Loading)
    val mangaChaptersStateDTO = _mangaChaptersStateDTO.asStateFlow()
    private val _chapterImages = MutableStateFlow<UiState<List<MangaPage>>>(UiState.Loading)
    val chapterImages = _chapterImages.asStateFlow()

    fun getChapterImages(chapterId: String) {
        _chapterImages.value = UiState.Loading
        viewModelScope.launch {
            getChapterImagesUseCase(chapterId)
                .onSuccess { images ->
                    _chapterImages.value = UiState.Success(images)
                }
                .onFailure { error ->
                    _chapterImages.value = UiState.Error(error.message ?: "Unknown error")
                }
        }
    }

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

}