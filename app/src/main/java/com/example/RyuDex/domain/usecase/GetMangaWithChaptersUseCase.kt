package com.example.RyuDex.domain.usecase

import com.example.RyuDex.data.repo.LocalMangaRepo
import com.example.RyuDex.model.entity.DownloadStatus
import com.example.RyuDex.model.entity.MangaWithChapters
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetMangaWithChaptersUseCase @Inject constructor(
    private val localMangaRepo: LocalMangaRepo
) {
    operator fun invoke() : Flow<List<MangaWithChapters>> {
        val mangaWithChapters = localMangaRepo.getAllMangaWithChapters()
        val result = mangaWithChapters.map { rawList ->
            rawList.sortedWith(
                compareBy<MangaWithChapters> { item ->
                    when(item.manga.downloadStatus){
                        DownloadStatus.PENDING -> 1
                        DownloadStatus.DOWNLOADING -> 0
                        DownloadStatus.PAUSED -> 2
                        DownloadStatus.FAILED -> 3
                        DownloadStatus.COMPLETED -> 4
                    }
                }.thenBy{ item -> item.manga.id }
            )
        }
        return result
    }
}