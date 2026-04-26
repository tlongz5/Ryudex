package com.example.RyuDex.domain.usecase

import com.example.RyuDex.data.repo.MangaRepo
import com.example.RyuDex.model.MangaItem
import javax.inject.Inject

class GetMangaListFromTagUseCase @Inject constructor(
    private val mangaRepo: MangaRepo
) {
    suspend operator fun invoke(
        includedTags: List<String>? = null,
        limit:Int
    ): Result<List<MangaItem>>{
        return mangaRepo.getMangaListFromTags(includedTags,limit)
    }
}