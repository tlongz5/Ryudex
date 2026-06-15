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
import com.example.RyuDex.model.entity.ChapterImageEntity
import com.example.RyuDex.model.entity.MangaCoverEntity
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@HiltWorker
class DownloadMangaWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val mangaApi: MangaApi,
    private val mangaDao: MangaDao,
    private val chapterImageDao: ChapterImageDao

) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        val mangaId = inputData.getString("MANGA_ID") ?: return@withContext Result.failure()
        val chapterId = inputData.getString("CHAPTER_ID") ?: return@withContext Result.failure()
        val urls = inputData.getStringArray("URLS") ?: return@withContext Result.failure()
        val urlCover = inputData.getString("URL_COVER") ?: return@withContext Result.failure()

        return@withContext try {
            Log.d("TAG", "Running download")
            val localPathMangaCover = downloadAndSaveImageCover(applicationContext,mangaApi,urlCover, mangaId)
            if(localPathMangaCover != null){
                mangaDao.updateImageLocal(mangaId,localPathMangaCover)
            }else return@withContext Result.retry()

            Log.d("TAG", "Run download 1")
            urls.forEachIndexed { index, url ->
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

                val progress = (index + 1) * 100 / urls.size
                setProgress(workDataOf(
                    "PROGRESS" to progress,
                    "MANGA_ID" to mangaId,
                    "CHAPTER_ID" to chapterId))
            }
            Log.d("TAG", "Run download success")
            Result.success()
        }catch (e:Exception){
            Log.d("TAG", "Run download fail")
            Result.retry()
        }
    }

}