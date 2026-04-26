package com.example.RyuDex.model

data class MangaItem(
    val id:String,
    val type: String,
    val attributes: MangaAttributes,
    val relationships: List<Relationship>  // get author only
)
