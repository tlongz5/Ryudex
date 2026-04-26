package com.example.RyuDex.model

data class Relationship(
    val id: String,
    val type: String,
    val attributes: RelationshipAttributes?
)
