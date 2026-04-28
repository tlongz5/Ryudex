package com.example.RyuDex.utils

import com.example.RyuDex.model.MangaCover
import com.example.RyuDex.model.MangaItem

fun MangaItem.toMangaCover(): MangaCover {
    val relationship = this.relationships
    val img = relationship.findLast { it.type == "cover_art" }?.attributes?.fileName
    val authorInfo = relationship.findLast { it.type == "author" }
    return MangaCover(
        id = this.id,
        title = getDisplayText(this.attributes.title),
        author = (authorInfo?.id ?: "Unknown") to
                (authorInfo?.attributes?.name ?: "Unknown"),
        img = img?.let { Constant.getCover(this.id, img) } ,
        category = this.attributes.tags.map { tagItem ->
            tagItem.id to getDisplayText(tagItem.attributes?.name)
        },
        description = getDisplayText(this.attributes.description),
        lastChapter = this.attributes.lastChapter,
        availableLanguages = this.attributes.availableTranslatedLanguages.filterNotNull(),
        year = this.attributes.year
    )
}

fun getDisplayText(text: Map<String, String>?): String {
    if (text.isNullOrEmpty()) return "Unknown"
    return text["en"] ?: text["ja-ro"] ?: text["ja"] ?: text["vi"] ?: text.values.firstOrNull()
    ?: "Unknown"
}

fun getImageLinkFromInfo(baseUrl:String,hash:String,link:String):String{
    return "$baseUrl/data/$hash/$link"
}