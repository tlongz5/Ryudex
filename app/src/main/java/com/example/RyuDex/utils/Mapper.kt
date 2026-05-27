package com.example.RyuDex.utils

import com.example.RyuDex.model.MangaCover
import com.example.RyuDex.model.dto.chapter.MangaChapterDTO
import com.example.RyuDex.model.dto.manga.MangaItemDTO
import com.example.RyuDex.model.entity.MangaChapterEntity
import com.example.RyuDex.model.entity.MangaCoverEntity

fun MangaItemDTO.toMangaCover(): MangaCover {
    val relationship = this.relationships
    val img = relationship.findLast { it.type == "cover_art" }?.attributes?.fileName
    val authorInfo = relationship.findLast { it.type == "author" }
    return MangaCover(
        id = this.id,
        title = getDisplayText(this.attributes.title),
        author = (authorInfo?.id ?: "Unknown") to
                (authorInfo?.attributes?.name ?: "Unknown"),
        img = img?.let { getCover(this.id, img) } ,
        category = this.attributes.tags.map { tagItem ->
            tagItem.id to getDisplayText(tagItem.attributes?.name)
        },
        description = getDisplayText(this.attributes.description),
        lastChapter = this.attributes.lastChapter,
        availableLanguages = this.attributes.availableTranslatedLanguages.filterNotNull(),
        year = this.attributes.year
    )
}

fun MangaChapterDTO.toMangaChapterEntity(mangaId: String): MangaChapterEntity {
    return MangaChapterEntity(
        chapterId = this.id,
        mangaId = mangaId,
        title = this.attributes.title,
        chapter = this.attributes.chapter,
        createdAt = this.attributes.createdAt,
        pages = this.attributes.pages,
        localPath = null,
        translatedLanguage = this.attributes.translatedLanguage
    )
}

fun MangaCover.toMangaCoverEntity(): MangaCoverEntity {
    return MangaCoverEntity(
        id = this.id,
        title = this.title,
        author = this.author,
        imgOnline = this.img,
        category = this.category,
        description = this.description,
        lastChapter = this.lastChapter,
        availableLanguages = this.availableLanguages,
        year = this.year,
        imgLocal = null,
        downloadStatus = "QUEUED",
        progress = 0
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

fun getCover(mangaId:String,fileName:String) : String{
    // lấy mangaId và tên file ảnh để lấy đường dẫn ảnh
    return "https://uploads.mangadex.org/covers/${mangaId}/${fileName}"
}


