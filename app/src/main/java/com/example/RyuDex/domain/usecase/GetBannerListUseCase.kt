package com.example.RyuDex.domain.usecase

import com.example.RyuDex.data.repo.RemoteMangaRepo
import com.example.RyuDex.model.dto.manga.MangaItemDTO
import javax.inject.Inject

class GetBannerListUseCase @Inject constructor(private val remoteMangaRepo: RemoteMangaRepo) {
    suspend operator fun invoke(): Result<List<MangaItemDTO>>{
        return remoteMangaRepo.getMangaBannerList()
    }
}