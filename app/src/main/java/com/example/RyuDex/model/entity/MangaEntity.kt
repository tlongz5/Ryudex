package com.example.RyuDex.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "manga")
data class MangaEntity(
    @PrimaryKey val id: String,
    val title: String?,
    val description: String?,
    val img: String?,
    val authorId: String?,
    val authorName: String?,
    val category: String,
    val lastChapter: String?,
    val availableLanguages: String,
    val year: Int?
)