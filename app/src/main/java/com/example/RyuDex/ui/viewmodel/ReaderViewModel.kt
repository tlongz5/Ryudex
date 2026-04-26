package com.example.RyuDex.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.RyuDex.domain.usecase.GetMangaImagesUseCase
import com.example.RyuDex.model.ChapterImages
import com.example.RyuDex.model.MangaPage
import com.example.RyuDex.model.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReaderViewModel @Inject constructor(
    private val getChapterImagesUseCase: GetMangaImagesUseCase
) : ViewModel() {
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

}