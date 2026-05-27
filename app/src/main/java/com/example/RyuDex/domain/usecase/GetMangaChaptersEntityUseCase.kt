package com.example.RyuDex.domain.usecase

import com.example.RyuDex.data.repo.LocalMangaRepo
import javax.inject.Inject

class GetMangaChaptersEntityUseCase @Inject constructor(
    private val localMangaRepo: LocalMangaRepo
) {
    suspend operator fun invoke(mangaId: String) = localMangaRepo.getMangaChaptersEntity(mangaId)
}