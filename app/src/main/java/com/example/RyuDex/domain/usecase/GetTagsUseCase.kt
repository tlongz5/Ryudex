package com.example.RyuDex.domain.usecase

import com.example.RyuDex.data.repo.RemoteMangaRepo
import com.example.RyuDex.model.dto.manga.TagItemDTO
import javax.inject.Inject

class GetTagsUseCase @Inject constructor(private val remoteMangaRepo: RemoteMangaRepo) {
    suspend operator fun invoke() : Result<List<TagItemDTO>>{
        return remoteMangaRepo.getTags()
    }
}