package com.example.RyuDex.model.entity

import androidx.room.Entity

@Entity(
    tableName = "chapter_images",
    primaryKeys = ["chapterId", "pageIndex"]
)
data class ChapterImageEntity(
    val chapterId: String,
    val pageIndex: Int,
    val localPath: String
)
