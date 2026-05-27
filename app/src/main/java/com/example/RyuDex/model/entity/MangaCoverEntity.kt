package com.example.RyuDex.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "manga")
data class MangaCoverEntity(
    @PrimaryKey val id: String,
    val title: String?,
    val description: String?,
    val imgOnline: String?,
    val imgLocal: String?,
    val author: Pair<String,String>, // id , name author
    val category: List<Pair<String,String>>,
    val lastChapter: String?,
    val availableLanguages: List<String>,
    val year: Int?,

    val downloadStatus: String,
    val progress: Int,
)