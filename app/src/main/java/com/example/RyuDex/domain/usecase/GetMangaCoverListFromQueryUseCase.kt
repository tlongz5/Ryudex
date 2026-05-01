package com.example.RyuDex.domain.usecase

import androidx.paging.PagingData
import com.example.RyuDex.data.repo.MangaRepo
import com.example.RyuDex.model.MangaCover
import com.example.RyuDex.model.MangaItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMangaCoverListFromQueryUseCase @Inject constructor(private val mangaRepo: MangaRepo) {
    operator fun invoke(
        title: String? = null,
        authors: List<String>? = null,
        includedTags: List<String>? = null,
        orderFollowedCount: String? = null,
        orderCreatedAt: String? = null,
        orderYear: String? = null,
        includes: List<String>? = null
    ): Flow<PagingData<MangaCover>> {
        return mangaRepo.getMangaCoverListFromQuery(
            title = title,
            authors = authors,
            includedTags = includedTags,
            orderFollowedCount = orderFollowedCount,
            orderCreatedAt = orderCreatedAt,
            orderYear = orderYear,
            includes = includes
        )
    }
}