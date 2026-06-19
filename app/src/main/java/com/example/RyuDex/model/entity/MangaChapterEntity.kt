package com.example.RyuDex.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "manga_chapter")
data class MangaChapterEntity (
    @PrimaryKey val chapterId: String,
    val mangaId: String,
    val chapter: String?,
    val title: String?,
    val translatedLanguage: String?,
    val totalPages: Int?,
    val createdAt: String?,

    val downloadedPages: Int = 0,
    val downloadStatus: DownloadStatus = DownloadStatus.PENDING
)