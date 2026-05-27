package com.example.RyuDex.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.RyuDex.model.entity.MangaCoverEntity

@Dao
interface MangaDao {
    @Query("SELECT * FROM manga")
    suspend fun getAllManga(): List<MangaCoverEntity>

    @Query("SELECT * FROM manga WHERE id = :id")
    suspend fun getMangaById(id: String): MangaCoverEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertManga(manga: MangaCoverEntity)

    @Query("Update manga set imgLocal = :localPath where id = :mangaId")
    suspend fun updateImageLocal(mangaId: String, localPath: String)
}