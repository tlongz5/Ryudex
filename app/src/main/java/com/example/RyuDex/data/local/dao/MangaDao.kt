package com.example.RyuDex.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.RyuDex.model.entity.DownloadStatus
import com.example.RyuDex.model.entity.MangaCoverEntity
import com.example.RyuDex.model.entity.MangaWithChapters
import kotlinx.coroutines.flow.Flow

@Dao
interface MangaDao {
    @Query("SELECT * FROM manga")
    suspend fun getAllManga(): List<MangaCoverEntity>

    @Query("SELECT * FROM manga WHERE id = :id")
    suspend fun getMangaById(id: String): MangaCoverEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertManga(manga: MangaCoverEntity)

    @Query("DELETE FROM manga WHERE id = :mangaId")
    suspend fun deleteChapterById(mangaId: String)


    @Query("UPDATE manga set imgLocal = :localPath where id = :mangaId")
    suspend fun updateImageLocal(mangaId: String, localPath: String)

    @Query("UPDATE manga_chapter SET downloadStatus = :pendingStatus WHERE downloadStatus = :downloadingStatus")
    suspend fun resetAllDownloadingToPending(
        pendingStatus: DownloadStatus = DownloadStatus.PENDING,
        downloadingStatus: DownloadStatus = DownloadStatus.DOWNLOADING
    )


    @Query("Update manga set downloadStatus = :downloadStatus where id = :mangaId")
    suspend fun updateDownloadStatus(mangaId: String, downloadStatus: DownloadStatus)

    @Transaction
    @Query("SELECT * FROM manga")
    fun getAllMangasWithChapters(): Flow<List<MangaWithChapters>>
}