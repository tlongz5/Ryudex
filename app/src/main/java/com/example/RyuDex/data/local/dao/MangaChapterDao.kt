package com.example.RyuDex.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.RyuDex.model.entity.DownloadStatus
import com.example.RyuDex.model.entity.MangaChapterEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MangaChapterDao {
    @Query("SELECT * FROM manga_chapter WHERE mangaId = :mangaId")
    suspend fun getMangaChapters(mangaId: String): List<MangaChapterEntity>

    @Query("SELECT * FROM manga_chapter WHERE chapterId = :chapterId")
    suspend fun getChapterById(chapterId:String) : MangaChapterEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMangaChapter(mangaChapter: MangaChapterEntity)

    @Query("UPDATE manga_chapter SET downloadStatus = :downloadStatus WHERE chapterId = :chapterId")
    suspend fun updateDownloadStatus(chapterId: String, downloadStatus: DownloadStatus)

    @Query("UPDATE manga_chapter SET downloadedPages = downloadedPages+1 WHERE chapterId = :chapterId")
    suspend fun updateDownloadedPages(chapterId: String)

    @Query("SELECT COUNT(chapterId) FROM manga_chapter WHERE mangaId = :mangaId AND downloadStatus != :statusCompleted")
    suspend fun countUncompletedChapters(
        mangaId: String,
        statusCompleted: DownloadStatus = DownloadStatus.COMPLETED
    ): Int

    @Query("SELECT * FROM manga_chapter WHERE downloadStatus IN (:statusPending, :statusDownloading)")
    suspend fun getAllUncompletedChapters(
        statusPending: DownloadStatus = DownloadStatus.PENDING,
        statusDownloading: DownloadStatus = DownloadStatus.DOWNLOADING
    ): List<MangaChapterEntity>

    @Query("""
        UPDATE manga_chapter 
        SET downloadStatus = :pausedStatus 
        WHERE mangaId = :mangaId 
        AND downloadStatus IN (:pendingStatus, :downloadingStatus)
    """)
    suspend fun updateDownloadStatusToPause(
        mangaId: String,
        pausedStatus: DownloadStatus,
        pendingStatus: DownloadStatus,
        downloadingStatus: DownloadStatus
    )

    @Query("UPDATE manga_chapter SET downloadStatus = :pendingStatus WHERE mangaId = :mangaId AND downloadStatus = :pausedStatus")
    suspend fun updateDownloadStatusToPending(
        mangaId: String,
        pendingStatus: DownloadStatus = DownloadStatus.PENDING,
        pausedStatus: DownloadStatus = DownloadStatus.PAUSED
    )

    @Query("UPDATE manga_chapter SET downloadStatus = :pendingStatus WHERE downloadStatus = :downloadingStatus")
    suspend fun resetAllDownloadingToPending(
        pendingStatus: DownloadStatus = DownloadStatus.PENDING,
        downloadingStatus: DownloadStatus = DownloadStatus.DOWNLOADING
    )


    @Query("DELETE FROM manga_chapter WHERE mangaId = :mangaId")
    suspend fun deleteChapterById(mangaId: String)

}