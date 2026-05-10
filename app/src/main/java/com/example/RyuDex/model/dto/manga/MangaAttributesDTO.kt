package com.example.RyuDex.model.dto.manga

data class MangaAttributesDTO(
    val title: Map<String,String>?,
    val description: Map<String,String>?,
    val lastChapter: String?,
    val status:String, //end or not end
    val year:Int?,
    val tags: List<TagItemDTO>, //type
    val availableTranslatedLanguages: List<String?>
)