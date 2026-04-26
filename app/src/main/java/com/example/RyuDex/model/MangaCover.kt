package com.example.RyuDex.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

// class này để hiển thị data
@Parcelize
data class MangaCover(
    val id: String,
    val title: String?,
    val description: String?,
    val img: String?,
    val author: Pair<String,String>, // id , name author
    val category: List<Pair<String,String>>,
    val lastChapter: String?,
    val availableLanguages: List<String>
): Parcelable