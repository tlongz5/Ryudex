package com.example.RyuDex.model.dto.manga

data class MangaItemDTO(
    val id:String,
    val type: String,
    val attributes: MangaAttributesDTO,
    val relationships: List<RelationshipDTO>  // get author only
)
