package com.example.RyuDex.di

import com.example.RyuDex.data.repo.MangaRepo
import com.example.RyuDex.domain.repoimpl.MangaRepoImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepoModule {
    @Binds
    @Singleton
    abstract fun provideMangaRepo(mangaRepoImpl: MangaRepoImpl): MangaRepo

}