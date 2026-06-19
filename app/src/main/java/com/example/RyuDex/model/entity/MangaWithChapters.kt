package com.example.RyuDex.model.entity

import androidx.room.Embedded
import androidx.room.Relation

data class MangaWithChapters(
    @Embedded
    val manga: MangaCoverEntity,

    @Relation(
        parentColumn = "id",       // Get primary key của MangaCoverEntity
        entityColumn = "mangaId"   // foreign key của MangaChapterEntity
    )
    val chapters: List<MangaChapterEntity>
)