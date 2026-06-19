package com.example.RyuDex.data.repo

import androidx.work.WorkInfo
import com.example.RyuDex.model.MangaCover
import com.example.RyuDex.model.dto.chapter.MangaChapterDTO
import com.example.RyuDex.model.entity.ChapterImageEntity
import com.example.RyuDex.model.entity.MangaChapterEntity
import com.example.RyuDex.model.entity.MangaCoverEntity
import com.example.RyuDex.model.entity.MangaWithChapters
import kotlinx.coroutines.flow.Flow

interface LocalMangaRepo {
    suspend fun requestDownloadManga(mangaCover: MangaCover, mangaToDownload: List<MangaChapterDTO>)
    fun getAllMangaWithChapters(): Flow<List<MangaWithChapters>>
    suspend fun getAllDownloadingManga(): Result<List<WorkInfo>>
    suspend fun cancelDownloadManga(mangaId: String)
    suspend fun prioritizeDownloadManga(mangaId: String)
    suspend fun pauseDownloadManga(mangaId: String)
    suspend fun getMangaChaptersEntity(mangaId: String): List<MangaChapterEntity>
    suspend fun getMangaChapterImagesEntity(chapterId: String): List<ChapterImageEntity>
    suspend fun getMangaCoversEntity(): List<MangaCoverEntity>
}