package com.example.RyuDex.domain.usecase

import com.example.RyuDex.data.repo.LocalMangaRepo
import com.example.RyuDex.utils.toMangaPage
import javax.inject.Inject

class GetChapterImagesFromLocalUseCase @Inject constructor(
    private val localMangaRepo: LocalMangaRepo
) {
    suspend operator fun invoke(chapterId: String) =
        localMangaRepo.getMangaChapterImagesEntity(chapterId)
            .sortedBy { it.pageIndex }
            .map { it.toMangaPage() }
}
