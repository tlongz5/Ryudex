package com.example.RyuDex.model.dto.chapter

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ChapterAttributesDTO(
    val chapter: String?,
    val title: String?,
    val translatedLanguage: String?,
    val pages: Int?,
    val createdAt: String?
): Parcelable
