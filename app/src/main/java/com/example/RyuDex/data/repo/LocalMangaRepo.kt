package com.example.RyuDex.data.repo

import com.example.RyuDex.model.MangaCover
import com.example.RyuDex.model.dto.chapter.MangaChapterDTO
import com.example.RyuDex.model.entity.ChapterImageEntity
import com.example.RyuDex.model.entity.MangaChapterEntity
import com.example.RyuDex.model.entity.MangaCoverEntity

interface LocalMangaRepo {
    suspend fun requestDownloadManga(mangaCover: MangaCover, mangaToDownload: List<MangaChapterDTO>)
    suspend fun cancelDownloadManga(mangaToCancel: MangaChapterEntity)

    suspend fun getMangaChaptersEntity(mangaId: String): List<MangaChapterEntity>
    suspend fun getMangaChapterImagesEntity(chapterId: String): List<ChapterImageEntity>
    suspend fun getMangaCoversEntity(): List<MangaCoverEntity>
}