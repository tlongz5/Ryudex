package com.example.RyuDex.domain.usecase

import com.example.RyuDex.data.repo.LocalMangaRepo
import javax.inject.Inject

class GetMangaChapterImagesEntity @Inject constructor(
    private val localMangaRepo: LocalMangaRepo
) {
    suspend operator fun invoke(chapterId: String) = localMangaRepo.getMangaChapterImagesEntity(chapterId)
}