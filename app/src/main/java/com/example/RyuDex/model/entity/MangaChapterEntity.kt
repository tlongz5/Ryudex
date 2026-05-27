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
    val pages: Int?,
    val createdAt: String?,

    val localPath: String?
)