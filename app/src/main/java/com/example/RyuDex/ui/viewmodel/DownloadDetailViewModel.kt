package com.example.RyuDex.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.RyuDex.domain.usecase.GetMangaCoversEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DownloadDetailViewModel @Inject constructor(
    private val getMangaCoversEntity: GetMangaCoversEntity
): ViewModel() {
    fun getMangaCovers() = viewModelScope.launch { getMangaCoversEntity() }
}