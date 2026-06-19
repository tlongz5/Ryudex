package com.example.RyuDex.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.WorkInfo
import com.example.RyuDex.domain.usecase.CancelDownloadMangaUseCase
import com.example.RyuDex.domain.usecase.GetAllDownloadingMangaUseCase
import com.example.RyuDex.domain.usecase.GetMangaCoversEntityUseCase
import com.example.RyuDex.domain.usecase.GetMangaWithChaptersUseCase
import com.example.RyuDex.domain.usecase.PauseDownloadMangaUseCase
import com.example.RyuDex.domain.usecase.PrioritizeDownloadMangaUseCase
import com.example.RyuDex.model.UiState
import com.example.RyuDex.model.entity.MangaCoverEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DownloadDetailViewModel @Inject constructor(
    private val getMangaCoversEntityUseCase: GetMangaCoversEntityUseCase,
    private val getAllDownloadingMangaUseCase: GetAllDownloadingMangaUseCase,
    private val getMangaWithChaptersUseCase: GetMangaWithChaptersUseCase,
    private val cancelDownloadMangaUseCase: CancelDownloadMangaUseCase,
    private val prioritizeDownloadMangaUseCase: PrioritizeDownloadMangaUseCase,
    private val pauseDownloadMangaUseCase: PauseDownloadMangaUseCase
): ViewModel() {

    val getMangaWithChapters = getMangaWithChaptersUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )
    private val _workInfosState =
        MutableStateFlow<UiState<List<WorkInfo>>>(UiState.Loading)
    val workInfosState = _workInfosState.asStateFlow()

    private val _mangaCoversState =
        MutableStateFlow<UiState<List<MangaCoverEntity>>>(UiState.Loading)
    val mangaCoversState = _mangaCoversState.asStateFlow()

    fun getMangaCovers() = viewModelScope.launch {
        _mangaCoversState.value = UiState.Loading
        _mangaCoversState.value = UiState.Success(getMangaCoversEntityUseCase())
    }

    fun getAllDownloadingManga() = viewModelScope.launch {
        _workInfosState.value = UiState.Loading
        getAllDownloadingMangaUseCase()
            .onSuccess { workInfos ->
                _workInfosState.value = UiState.Success(workInfos)
            }
            .onFailure { exception ->
                _workInfosState.value =
                    UiState.Error(exception.message ?: "Unknown error")
            }
    }

    fun cancelDownloadManga(mangaId:String) = viewModelScope.launch {
        cancelDownloadMangaUseCase(mangaId)
    }

    fun prioritizeDownloadManga(mangaId:String) = viewModelScope.launch {
        prioritizeDownloadMangaUseCase(mangaId)
    }

    fun pauseDownloadManga(mangaId:String) = viewModelScope.launch {
        pauseDownloadMangaUseCase(mangaId)
    }
}