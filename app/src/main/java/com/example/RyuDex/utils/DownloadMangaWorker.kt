package com.example.RyuDex.utils

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.RyuDex.data.local.dao.ChapterImageDao
import com.example.RyuDex.data.local.dao.MangaChapterDao
import com.example.RyuDex.data.local.dao.MangaDao
import com.example.RyuDex.data.remote.MangaApi
import com.example.RyuDex.data.repo.RemoteMangaRepo
import com.example.RyuDex.model.entity.ChapterImageEntity
import com.example.RyuDex.model.entity.DownloadStatus
import com.example.RyuDex.model.entity.MangaCoverEntity
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

@HiltWorker
class DownloadMangaWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val mangaApi: MangaApi,
    private val mangaDao: MangaDao,
    private val chapterImageDao: ChapterImageDao,
    private val mangaChapterDao: MangaChapterDao,
    private val remoteMangaRepo: RemoteMangaRepo

) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        val mangaId = inputData.getString("MANGA_ID") ?: return@withContext Result.failure()
        val chapterId = inputData.getString("CHAPTER_ID") ?: return@withContext Result.failure()
        val urlCover = inputData.getString("URL_COVER") ?: return@withContext Result.failure()

        Log.d("TAG","Run in CoroutineWorker")
        val currentChapter = mangaChapterDao.getChapterById(chapterId)
        if(currentChapter.downloadStatus == DownloadStatus.COMPLETED) return@withContext Result.success()

        return@withContext try {
            val result = remoteMangaRepo.getMangaImages(chapterId)
            if(!result.isSuccess) return@withContext Result.retry()

            val chapterImages = result.getOrThrow()
            val urls = chapterImages.chapter.data.map { data ->
                getImageLinkFromInfo(chapterImages.baseUrl, chapterImages.chapter.hash, data)
            }

            Log.d("TAG","Run in CoroutineWorker 2")

            val localPathMangaCover = downloadAndSaveImageCover(applicationContext,mangaApi,urlCover, mangaId)
            if(localPathMangaCover != null){
                mangaDao.updateImageLocal(mangaId,localPathMangaCover)

            }else return@withContext Result.retry()

            Log.d("TAG", "Run download 1")


            //DOWNLOADING
            mangaDao.updateDownloadStatus(mangaId, DownloadStatus.DOWNLOADING)
            mangaChapterDao.updateDownloadStatus(chapterId, DownloadStatus.DOWNLOADING)

            Log.d("TAG", "Run download 2")

            urls.forEachIndexed { index, url ->
                val localImage = chapterImageDao.getChapterImage(chapterId,index)
                if(localImage != null) return@forEachIndexed

                val localPath = downloadAndSaveImage(
                    context = applicationContext,
                    mangaApi = mangaApi,
                    imageUrl = url,
                    mangaId = mangaId,
                    chapterId = chapterId,
                    pageNumber = index
                ) ?: return@withContext Result.retry()

                chapterImageDao.insertChapterImages(
                    ChapterImageEntity(
                        chapterId = chapterId,
                        pageIndex = index,
                        localPath = localPath
                    )
                )
                mangaChapterDao.updateDownloadedPages(chapterId)

                delay(200)
            }

            // COMPLETED CHAPTER
            mangaChapterDao.updateDownloadStatus(chapterId, DownloadStatus.COMPLETED)
            Log.d("TAG", "Run download success")

            val pendingCount = mangaChapterDao.countUncompletedChapters(mangaId)
            if (pendingCount == 0) {

                // UPDATE DAO manga
                mangaDao.updateDownloadStatus(mangaId, DownloadStatus.COMPLETED)
            }

            Log.d("TAG", "Run download success ")

            Result.success()
        }catch (e:Exception){
            Log.d("TAG", "Run download fail")
            Result.retry()
        }
    }

}