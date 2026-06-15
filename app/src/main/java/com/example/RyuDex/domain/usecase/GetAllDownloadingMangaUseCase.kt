package com.example.RyuDex.domain.usecase

import androidx.work.WorkInfo
import com.example.RyuDex.data.repo.LocalMangaRepo
import javax.inject.Inject

class GetAllDownloadingMangaUseCase @Inject constructor(
    private val localMangaRepo: LocalMangaRepo
) {
    suspend operator fun invoke(): Result<List<WorkInfo>> = localMangaRepo.getAllDownloadingManga()
}