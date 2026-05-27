package com.example.RyuDex.utils

import android.content.Context
import com.example.RyuDex.data.remote.MangaApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

suspend fun downloadAndSaveImage(context: Context, mangaApi: MangaApi, imageUrl: String, mangaId: String, chapterId: String, pageNumber: Int): String?
    = withContext(Dispatchers.IO) {
        try {
            val response = mangaApi.downloadImage(imageUrl)

            if (response.isSuccessful) {
                val responseBody = response.body() ?: return@withContext null

                val directory = File(context.filesDir, "$mangaId/$chapterId")
                if (!directory.exists()) {
                    directory.mkdirs()
                }

                val file = File(directory, "$pageNumber.jpg")

                file.outputStream().use { outputStream ->
                    responseBody.byteStream().use { inputStream ->
                        inputStream.copyTo(outputStream)
                    }
                }

                file.absolutePath
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

suspend fun downloadAndSaveImageCover(context: Context, mangaApi: MangaApi, imageUrl: String, mangaId: String): String?
        = withContext(Dispatchers.IO) {
    try {
        val response = mangaApi.downloadImage(imageUrl)

        if (response.isSuccessful) {
            val responseBody = response.body() ?: return@withContext null

            val directory = File(context.filesDir, "covers")
            if (!directory.exists()) {
                directory.mkdirs()
            }

            val file = File(directory, "$mangaId.jpg")

            file.outputStream().use { outputStream ->
                responseBody.byteStream().use { inputStream ->
                    inputStream.copyTo(outputStream)
                }
            }

            file.absolutePath
        } else {
            null
        }
    } catch (e: Exception) {
        null
    }
}