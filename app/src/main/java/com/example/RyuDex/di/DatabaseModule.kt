package com.example.RyuDex.di

import android.app.Application
import androidx.room.Room
import com.example.RyuDex.data.local.MangaDatabase
import com.example.RyuDex.data.local.dao.ChapterImageDao
import com.example.RyuDex.data.local.dao.MangaChapterDao
import com.example.RyuDex.data.local.dao.MangaDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlin.jvm.java

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(application: Application): MangaDatabase {
        return Room.databaseBuilder(
            application,
            MangaDatabase::class.java,
            "app_database"
        ).build()
    }

    @Provides
    fun provideMangaDao(database: MangaDatabase): MangaDao {
        return database.mangaDao()
    }

    @Provides
    fun provideChapterImageDao(database: MangaDatabase): ChapterImageDao {
        return database.chapterImageDao()
    }

    @Provides
    fun provideMangaChapterDao(database: MangaDatabase): MangaChapterDao {
        return database.mangaChapterDao()
    }
}
