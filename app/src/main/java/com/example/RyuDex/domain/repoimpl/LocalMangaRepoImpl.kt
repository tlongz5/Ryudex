package com.example.RyuDex.domain.repoimpl

import android.content.Context
import android.util.Log
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import kotlinx.coroutines.guava.await
import androidx.work.workDataOf
import com.example.RyuDex.data.local.dao.ChapterImageDao
import com.example.RyuDex.data.local.dao.MangaChapterDao
import com.example.RyuDex.data.local.dao.MangaDao
import com.example.RyuDex.data.repo.LocalMangaRepo
import com.example.RyuDex.data.repo.RemoteMangaRepo
import com.example.RyuDex.model.MangaCover
import com.example.RyuDex.model.dto.chapter.MangaChapterDTO
import com.example.RyuDex.model.entity.ChapterImageEntity
import com.example.RyuDex.model.entity.DownloadStatus
import com.example.RyuDex.model.entity.MangaChapterEntity
import com.example.RyuDex.model.entity.MangaCoverEntity
import com.example.RyuDex.model.entity.MangaWithChapters
import com.example.RyuDex.utils.DownloadMangaWorker
import com.example.RyuDex.utils.getImageLinkFromInfo
import com.example.RyuDex.utils.toMangaChapterEntity
import com.example.RyuDex.utils.toMangaCoverEntity
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import java.io.File
import javax.inject.Inject

class LocalMangaRepoImpl @Inject constructor(
    private val workManager: WorkManager,
    private val mangaDao: MangaDao,
    private val chapterImageDao: ChapterImageDao,
    private val mangaChapterDao: MangaChapterDao
): LocalMangaRepo{
    override suspend fun requestDownloadManga(mangaCover: MangaCover, mangaToDownload: List<MangaChapterDTO>) {
        mangaDao.insertManga(mangaCover.toMangaCoverEntity())
        mangaToDownload.forEach { chapter ->
            val existingChapter = mangaChapterDao.getChapterById(chapter.id)
            if (existingChapter != null && existingChapter.downloadStatus != DownloadStatus.FAILED) {
                return@forEach
            }

            try {
                mangaChapterDao.insertMangaChapter(chapter.toMangaChapterEntity(mangaCover.id))

                //PENDING mangaCoverEntity
                mangaDao.updateDownloadStatus(mangaCover.id, DownloadStatus.PENDING)

                Log.d("TAG", "insertMangaChapter")
            }catch (e:Exception){
                Log.d("TAG", "insertMangaChapter: ${e.message}")
            }

            val inputData = workDataOf(
                "MANGA_ID" to mangaCover.id,
                "CHAPTER_ID" to chapter.id,
                "URL_COVER" to mangaCover.img
            )
            Log.d("TAG", "build inputData")

            val constraints = Constraints.Builder()
                .setRequiresStorageNotLow(true)
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

            val request = OneTimeWorkRequestBuilder<DownloadMangaWorker>()
                .setConstraints(constraints)
                .setInputData(inputData)
                .addTag(mangaCover.id) // tag id manga để cancel khi cần
                .addTag("manga_download") // để cancel tất cả download khi cần
                .build()

            Log.d("TAG", "build requestDownloadManga")

            workManager.enqueueUniqueWork(
                "GLOBAL_DOWNLOAD_QUEUE", //All task sẽ chạy tuần tự từ nguồn ở đây
                androidx.work.ExistingWorkPolicy.APPEND_OR_REPLACE,
                request
            )

            Log.d("TAG","running requestDownloadManga")
        }
    }

    override suspend fun prioritizeDownloadManga(mangaId: String) {
        mangaChapterDao.resetAllDownloadingToPending()
        mangaDao.resetAllDownloadingToPending()

        mangaDao.updateDownloadStatus(mangaId, DownloadStatus.PENDING)
        mangaChapterDao.updateDownloadStatusToPending(mangaId)

        workManager.cancelUniqueWork("GLOBAL_DOWNLOAD_QUEUE")

        val allPendingChapters = mangaChapterDao.getAllUncompletedChapters()
        if (allPendingChapters.isEmpty()) return

        val mangaProgressMap = allPendingChapters.groupBy { it.mangaId }.mapValues { entry ->
            val chapters = entry.value
            val downloaded = chapters.sumOf { it.downloadedPages }
            val total = chapters.sumOf { it.totalPages ?: 0 }

            if (total > 0) downloaded.toDouble() / total else 0.0
        }

        val sortedChapters = allPendingChapters.sortedWith(
            compareByDescending<MangaChapterEntity> { it.mangaId == mangaId }
                .thenByDescending { mangaProgressMap[it.mangaId] ?: 0.0 }
                .thenBy { it.chapterId }
        )

        sortedChapters.forEachIndexed { index, chapter ->
            val cover = mangaDao.getMangaById(chapter.mangaId)

            val urlCover = cover?.imgOnline ?: ""

            val inputData = workDataOf(
                "MANGA_ID" to chapter.mangaId,
                "CHAPTER_ID" to chapter.chapterId,
                "URL_COVER" to urlCover
            )

            val constraints = Constraints.Builder()
                .setRequiresStorageNotLow(true)
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

            val request = OneTimeWorkRequestBuilder<DownloadMangaWorker>()
                .setConstraints(constraints)
                .setInputData(inputData)
                .addTag(chapter.mangaId)
                .addTag("manga_download")
                .build()

            if (index == 0) {
                workManager.enqueueUniqueWork(
                    "GLOBAL_DOWNLOAD_QUEUE",
                    androidx.work.ExistingWorkPolicy.REPLACE,
                    request
                )
            } else {
                workManager.enqueueUniqueWork(
                    "GLOBAL_DOWNLOAD_QUEUE",
                    androidx.work.ExistingWorkPolicy.APPEND_OR_REPLACE,
                    request
                )
            }
        }
    }

    override fun getAllMangaWithChapters(): Flow<List<MangaWithChapters>> {
        return mangaDao.getAllMangasWithChapters()
    }

    override suspend fun getAllDownloadingManga(): Result<List<WorkInfo>> {
        return runCatching {
            workManager.getWorkInfosByTag("manga_download").await()
        }
    }

    override suspend fun cancelDownloadManga(mangaId: String) {
        mangaDao.deleteChapterById(mangaId)
        mangaChapterDao.deleteChapterById(mangaId)

        val currentDownloadingManga = mangaChapterDao.getAllUncompletedChapters().firstOrNull()?.mangaId ?: ""
        prioritizeDownloadManga(currentDownloadingManga)
    }

    override suspend fun pauseDownloadManga(mangaId: String) {
        mangaDao.updateDownloadStatus(mangaId, DownloadStatus.PAUSED)
        mangaChapterDao.updateDownloadStatusToPause(
            mangaId = mangaId,
            pausedStatus = DownloadStatus.PAUSED,
            pendingStatus = DownloadStatus.PENDING,
            downloadingStatus = DownloadStatus.DOWNLOADING
        )

        val currentDownloadingManga = mangaChapterDao.getAllUncompletedChapters().firstOrNull()?.mangaId ?: ""
        prioritizeDownloadManga(currentDownloadingManga)
    }

    override suspend fun getMangaChaptersEntity(mangaId: String): List<MangaChapterEntity> {
        return mangaChapterDao.getMangaChapters(mangaId)
    }

    override suspend fun getMangaChapterImagesEntity(
        chapterId: String
    ): List<ChapterImageEntity> {
        return chapterImageDao.getChapterImages(chapterId)
    }

    override suspend fun getMangaCoversEntity(): List<MangaCoverEntity> {
        return mangaDao.getAllManga()
    }
}