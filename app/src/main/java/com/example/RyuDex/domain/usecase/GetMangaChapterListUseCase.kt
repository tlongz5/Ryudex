package com.example.RyuDex.domain.usecase

import com.example.RyuDex.data.repo.MangaRepo
import com.example.RyuDex.model.dto.chapter.MangaChapterDTO
import javax.inject.Inject

class GetMangaChapterListUseCase @Inject constructor(private val mangaRepo: MangaRepo) {
    suspend operator fun invoke(id:String): Result<List<MangaChapterDTO>>{
        return mangaRepo.getMangaChapterList(id)
    }
}