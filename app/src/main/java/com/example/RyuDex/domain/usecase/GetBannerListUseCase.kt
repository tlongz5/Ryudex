package com.example.RyuDex.domain.usecase

import com.example.RyuDex.data.repo.MangaRepo
import com.example.RyuDex.model.dto.manga.MangaItemDTO
import javax.inject.Inject

class GetBannerListUseCase @Inject constructor(private val mangaRepo: MangaRepo) {
    suspend operator fun invoke(): Result<List<MangaItemDTO>>{
        return mangaRepo.getMangaBannerList()
    }
}