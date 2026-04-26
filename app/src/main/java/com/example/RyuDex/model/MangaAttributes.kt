package com.example.RyuDex.model

data class MangaAttributes(
    val title: Map<String,String>?,
    val description: Map<String,String>?,
    val lastChapter: String?,
    val status:String, //end or not end
    val year:Int?,
    val tags: List<TagItem>, //type
    val availableTranslatedLanguages: List<String?>
)