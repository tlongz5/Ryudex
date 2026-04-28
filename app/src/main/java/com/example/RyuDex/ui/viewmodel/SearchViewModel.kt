package com.example.RyuDex.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.RyuDex.data.repo.MangaRepo
import com.example.RyuDex.domain.usecase.GetMangaChapterListUseCase
import com.example.RyuDex.domain.usecase.GetMangaListFromTagUseCase
import com.example.RyuDex.model.MangaChapter
import com.example.RyuDex.model.MangaCover
import com.example.RyuDex.model.UiState
import com.example.RyuDex.utils.toMangaCover
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getMangaListFromTagUseCase: GetMangaListFromTagUseCase
) : ViewModel() {
    private val _mangaCoversState = MutableStateFlow<UiState<List<MangaCover>>>(UiState.Loading)
    val mangaCoversState = _mangaCoversState.asStateFlow()

    fun getMangaHotList(){
        viewModelScope.launch {
            _mangaCoversState.value = UiState.Loading
            val response = getMangaListFromTagUseCase(null,20)
            response.onSuccess { mangaItems ->
                _mangaCoversState.value = UiState.Success(mangaItems.map { it.toMangaCover() })
            }.onFailure { exception ->
                _mangaCoversState.value = UiState.Error(exception?.message ?: "Unknown Error")
            }
        }
    }
}