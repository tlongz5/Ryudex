package com.example.RyuDex.domain.usecase

import com.example.RyuDex.data.repo.LocalMangaRepo
import com.example.RyuDex.model.entity.MangaCoverEntity
import javax.inject.Inject

class GetMangaCoversEntityUseCase @Inject constructor(
    private val localMangaRepo: LocalMangaRepo
) {
    suspend operator fun invoke(): List<MangaCoverEntity> = localMangaRepo.getMangaCoversEntity()
}