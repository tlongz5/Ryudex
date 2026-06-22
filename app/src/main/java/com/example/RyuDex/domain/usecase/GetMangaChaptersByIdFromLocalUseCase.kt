package com.example.RyuDex.domain.usecase

import com.example.RyuDex.data.repo.LocalMangaRepo
import com.example.RyuDex.utils.toMangaChapterDTO
import javax.inject.Inject

class GetMangaChaptersByIdFromLocalUseCase @Inject constructor(
    private val localMangaRepo: LocalMangaRepo
) {
    suspend operator fun invoke(mangaId: String) =
        localMangaRepo.getMangaChaptersEntity(mangaId)
            .map { it.toMangaChapterDTO() }
}