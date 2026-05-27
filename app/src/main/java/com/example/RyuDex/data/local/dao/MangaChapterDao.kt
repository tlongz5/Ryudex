package com.example.RyuDex.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.RyuDex.model.entity.MangaChapterEntity

@Dao
interface MangaChapterDao {
    @Query("SELECT * FROM manga_chapter WHERE mangaId = :mangaId")
    suspend fun getMangaChapters(mangaId: String): List<MangaChapterEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMangaChapter(mangaChapter: MangaChapterEntity)

}