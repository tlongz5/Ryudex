package com.example.RyuDex.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.RyuDex.model.entity.ChapterImageEntity

@Dao
interface ChapterImageDao {
    @Query("SELECT * FROM chapter_images WHERE chapterId = :chapterId")
    suspend fun getChapterImages(chapterId: String): List<ChapterImageEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChapterImages(chapterImages: ChapterImageEntity)

    @Query("SELECT * FROM chapter_images WHERE chapterId = :chapterId AND pageIndex = :pageIndex")
    suspend fun getChapterImage(chapterId: String, pageIndex: Int): ChapterImageEntity?

}