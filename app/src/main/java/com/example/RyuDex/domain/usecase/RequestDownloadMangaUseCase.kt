package com.example.RyuDex.domain.usecase

import com.example.RyuDex.data.repo.LocalMangaRepo
import com.example.RyuDex.model.MangaCover
import com.example.RyuDex.model.dto.chapter.MangaChapterDTO
import javax.inject.Inject

class RequestDownloadMangaUseCase @Inject constructor(
    private val localMangaRepo: LocalMangaRepo
) {
    suspend operator fun invoke(mangaCover: MangaCover, mangaToDownload: List<MangaChapterDTO>) {
        localMangaRepo.requestDownloadManga(mangaCover, mangaToDownload)
    }
}