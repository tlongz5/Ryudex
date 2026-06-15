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
import com.example.RyuDex.model.entity.MangaChapterEntity
import com.example.RyuDex.model.entity.MangaCoverEntity
import com.example.RyuDex.utils.DownloadMangaWorker
import com.example.RyuDex.utils.getImageLinkFromInfo
import com.example.RyuDex.utils.toMangaChapterEntity
import com.example.RyuDex.utils.toMangaCoverEntity
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class LocalMangaRepoImpl @Inject constructor(
    private val workManager: WorkManager,
    private val mangaDao: MangaDao,
    private val chapterImageDao: ChapterImageDao,
    private val mangaChapterDao: MangaChapterDao,
    private val remoteMangaRepo: RemoteMangaRepo
): LocalMangaRepo{
    override suspend fun requestDownloadManga(mangaCover: MangaCover, mangaToDownload: List<MangaChapterDTO>) {
        mangaDao.insertManga(mangaCover.toMangaCoverEntity())
        mangaToDownload.forEach { chapter ->
            mangaChapterDao.insertMangaChapter(chapter.toMangaChapterEntity(mangaCover.id))
            // Lỗi
            Log.d("TAG", "insertMangaChapter")
            val result = remoteMangaRepo.getMangaImages(chapter.id)
            Log.d("TAG", "getMangaImages")
            if(result.isSuccess){
                val chapterImages = result.getOrThrow()
                val inputData = workDataOf(
                    "MANGA_ID" to mangaCover.id,
                    "CHAPTER_ID" to chapter.id,
                    "URLS" to chapterImages.chapter.data.map { data->
                        getImageLinkFromInfo(
                            chapterImages.baseUrl,
                            chapterImages.chapter.hash,
                            data
                        )}.toTypedArray(),
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
                    chapter.id,
                    androidx.work.ExistingWorkPolicy.KEEP,
                    request
                )

                Log.d("TAG","running requestDownloadManga")
            }else Log.d("TAG", "requestDownloadManga fail ${result.exceptionOrNull()?.message}")
        }
    }

    override suspend fun getAllDownloadingManga(): Result<List<WorkInfo>> {
        return runCatching {
            workManager.getWorkInfosByTag("manga_download").await()
        }
    }

    override suspend fun cancelDownloadManga(mangaToCancel: MangaChapterEntity) {
        workManager.cancelAllWorkByTag(mangaToCancel.mangaId)
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