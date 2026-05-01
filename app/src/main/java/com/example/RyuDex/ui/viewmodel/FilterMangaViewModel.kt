package com.example.RyuDex.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.RyuDex.domain.usecase.GetMangaCoverListFromQueryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FilterMangaViewModel @Inject constructor(
    private val getMangaListFromQueryUseCase: GetMangaCoverListFromQueryUseCase
): ViewModel() {
    fun getMangaList(
        title: String? = null,
        authors: List<String>? = null,
        includedTags: List<String>? = null
    ) = getMangaListFromQueryUseCase(
        title = title,
        authors = authors,
        includedTags = includedTags
    ).cachedIn(viewModelScope)

}