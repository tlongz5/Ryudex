package com.example.RyuDex.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.RyuDex.domain.usecase.GetMangaChapterListUseCase
import com.example.RyuDex.domain.usecase.GetMangaCoverListFromQueryUseCase
import com.example.RyuDex.domain.usecase.GetTagsUseCase
import com.example.RyuDex.model.UiState
import com.example.RyuDex.model.dto.manga.TagItemDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val getMangaCoverListFromQueryUseCase: GetMangaCoverListFromQueryUseCase,
    private val getTagsUseCase: GetTagsUseCase
): ViewModel() {
    private val _tagsState = MutableStateFlow<UiState<List<TagItemDTO>>>(UiState.Loading)
    val tagsState = _tagsState.asStateFlow()
    fun getMangaListFromQuery(
        language:String? = null,
        contentRating:String? = null,
        state:String? = null,
        tags:List<String>? = null
    ) = getMangaCoverListFromQueryUseCase(
        status = state?.let { listOf(it) },
        contentRating = contentRating?.let { listOf(it) },
        availableTranslatedLanguage = language?.let { listOf(language) },
        includedTags =  tags).cachedIn(viewModelScope)

    fun getTags(){
        _tagsState.value = UiState.Loading
        viewModelScope.launch {
            val response = getTagsUseCase()
            response.onSuccess { tagItems ->
                _tagsState.value = UiState.Success(tagItems)
            }.onFailure { exception ->
                _tagsState.value = UiState.Error(exception?.message?:"Unknown Error")
            }
        }
    }



}