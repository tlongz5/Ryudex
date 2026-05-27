package com.example.RyuDex.di

import com.example.RyuDex.data.repo.LocalMangaRepo
import com.example.RyuDex.data.repo.RemoteMangaRepo
import com.example.RyuDex.domain.repoimpl.LocalMangaRepoImpl
import com.example.RyuDex.domain.repoimpl.RemoteMangaRepoImpl
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
    abstract fun provideRemoteMangaRepo(remoteMangaRepoImpl: RemoteMangaRepoImpl): RemoteMangaRepo

    @Binds
    @Singleton
    abstract fun provideLocalMangaRepo(localMangaRepoImpl: LocalMangaRepoImpl): LocalMangaRepo
}