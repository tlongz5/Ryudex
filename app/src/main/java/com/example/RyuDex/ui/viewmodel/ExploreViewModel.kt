package com.example.RyuDex.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.RyuDex.domain.usecase.GetMangaChapterListUseCase
import com.example.RyuDex.domain.usecase.GetMangaCoverListFromQueryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val getMangaCoverListFromQueryUseCase: GetMangaCoverListFromQueryUseCase
): ViewModel() {
    fun getMangaListFromQuery(tags:List<String>?) = getMangaCoverListFromQueryUseCase(includedTags =  tags).cachedIn(viewModelScope)


}