package com.example.RyuDex.model

data class ApiResponse<T>(
    val data: List<T>,
    val limit: Int,
    val offset: Int,
    val total: Int
)
