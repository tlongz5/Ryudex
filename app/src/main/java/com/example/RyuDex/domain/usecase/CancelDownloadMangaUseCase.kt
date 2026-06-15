package com.example.RyuDex.domain.usecase

import com.example.RyuDex.data.repo.LocalMangaRepo
import com.example.RyuDex.model.entity.MangaChapterEntity
import javax.inject.Inject

class CancelDownloadMangaUseCase @Inject constructor(
    private val localMangaRepo: LocalMangaRepo
) {
    suspend operator fun invoke(mangaToCancel: MangaChapterEntity) {
        localMangaRepo.cancelDownloadManga(mangaToCancel)
    }
}