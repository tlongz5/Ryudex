package com.example.RyuDex.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.RyuDex.data.local.dao.ChapterImageDao
import com.example.RyuDex.data.local.dao.MangaChapterDao
import com.example.RyuDex.data.local.dao.MangaDao
import com.example.RyuDex.model.entity.ChapterImageEntity
import com.example.RyuDex.model.entity.MangaChapterEntity
import com.example.RyuDex.model.entity.MangaCoverEntity
import com.example.RyuDex.utils.Converters

@Database(entities = [MangaCoverEntity::class, MangaChapterEntity::class, ChapterImageEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class MangaDatabase : RoomDatabase() {
    abstract fun mangaDao(): MangaDao
    abstract fun chapterImageDao(): ChapterImageDao
    abstract fun mangaChapterDao(): MangaChapterDao
}