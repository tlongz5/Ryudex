package com.example.RyuDex.model.dto.manga

data class RelationshipDTO(
    val id: String,
    val type: String,
    val attributes: RelationshipAttributesDTO?
)
