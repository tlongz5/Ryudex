package com.example.RyuDex.domain.usecase

import com.example.RyuDex.data.repo.MangaRepo
import com.example.RyuDex.model.MangaPage
import javax.inject.Inject

class GetMangaImagesUseCase @Inject constructor(private val mangaRepo: MangaRepo) {
    suspend operator fun invoke(chapterId:String) : Result<List<MangaPage>> {
        val result = mangaRepo.getMangaImages(chapterId)
        return result.map { response ->
            response.chapter.data.mapIndexed { index,fileName ->
                MangaPage(
                    chapterId = chapterId,
                    pageIdx = index,
                    imageUrl =  response.baseUrl + "/data/${response.chapter.hash}/" + fileName
                )
            }
        }
    }
}