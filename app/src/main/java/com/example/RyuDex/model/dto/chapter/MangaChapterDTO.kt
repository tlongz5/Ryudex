package com.example.RyuDex.model.dto.chapter

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MangaChapterDTO(
    val id:String,
    val attributes: ChapterAttributesDTO
): Parcelable
