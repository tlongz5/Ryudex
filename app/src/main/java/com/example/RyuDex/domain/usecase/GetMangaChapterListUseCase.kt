package com.example.RyuDex.domain.usecase

import com.example.RyuDex.data.repo.MangaRepo
import com.example.RyuDex.model.MangaChapter
import javax.inject.Inject

class GetMangaChapterListUseCase @Inject constructor(private val mangaRepo: MangaRepo) {
    suspend operator fun invoke(id:String): Result<List<MangaChapter>>{
        return mangaRepo.getMangaChapterList(id)
    }
}