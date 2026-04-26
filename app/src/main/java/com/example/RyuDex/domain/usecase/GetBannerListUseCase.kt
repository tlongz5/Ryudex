package com.example.RyuDex.domain.usecase

import com.example.RyuDex.data.repo.MangaRepo
import com.example.RyuDex.model.MangaItem
import javax.inject.Inject

class GetBannerListUseCase @Inject constructor(private val mangaRepo: MangaRepo) {
    suspend operator fun invoke(): Result<List<MangaItem>>{
        return mangaRepo.getMangaBannerList()
    }
}