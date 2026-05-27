package com.example.RyuDex.domain.usecase

import androidx.paging.PagingData
import com.example.RyuDex.data.repo.RemoteMangaRepo
import com.example.RyuDex.model.MangaCover
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMangaCoverListFromQueryUseCase @Inject constructor(private val remoteMangaRepo: RemoteMangaRepo) {
    operator fun invoke(
        title: String? = null,
        authors: List<String>? = null,
        includedTags: List<String>? = null,
        orderFollowedCount: String? = null,
        orderCreatedAt: String? = null,
        orderYear: String? = null,
        status:List<String>? = null,
        contentRating:List<String>? = null,
        availableTranslatedLanguage:List<String>? = null,
        includes: List<String>? = null
    ): Flow<PagingData<MangaCover>> {
        return remoteMangaRepo.getMangaCoverListFromQuery(
            title = title,
            authors = authors,
            includedTags = includedTags,
            orderFollowedCount = orderFollowedCount,
            orderCreatedAt = orderCreatedAt,
            orderYear = orderYear,
            status = status,
            availableTranslatedLanguage = availableTranslatedLanguage,
            contentRating = contentRating,
            includes = includes
        )
    }
}