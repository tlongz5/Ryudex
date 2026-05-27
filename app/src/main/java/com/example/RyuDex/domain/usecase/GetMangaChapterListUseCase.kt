package com.example.RyuDex.domain.usecase

import com.example.RyuDex.data.repo.RemoteMangaRepo
import com.example.RyuDex.model.dto.chapter.MangaChapterDTO
import javax.inject.Inject

class GetMangaChapterListUseCase @Inject constructor(private val remoteMangaRepo: RemoteMangaRepo) {
    suspend operator fun invoke(id:String): Result<List<MangaChapterDTO>>{
        return remoteMangaRepo.getMangaChapterList(id)
    }
}