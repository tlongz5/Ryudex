package com.example.RyuDex.utils

object Constant {

    const val SEARCH_HISTORY = "search_history"
    const val BASE_URL = "https://api.mangadex.org/"  //api to get data

    // lấy thông tin tác giả và banner truyện
    val requires = listOf("author","cover_art")

    fun getCover(mangaId:String,fileName:String) : String{
        return "https://uploads.mangadex.org/covers/${mangaId}/${fileName}"
    }

    val POPULAR_TAGS = mapOf(
        null to "Hot",
        "391b0423-d847-456f-aff0-8b0cfc03066b" to "Action",
        "423e2eae-a7a2-4a8b-ac03-a8351462d71d" to "Romance",
        "e197df38-d0e7-43b5-9b09-2842d0c326dd" to "Web Comic",
        "4d32cc48-9f00-4cca-9b5a-a839f0764984" to "Comedy",
        "ace04997-f6bd-436e-b261-779182193d3d" to "Isekai",
        "cdc58593-87dd-415e-bbc0-2ec27bf404cc" to "Fantasy",
        "f5ba408b-0e7a-484d-8d49-4e9125ac96de" to "Full Color",
        "caaa44eb-cd40-4177-b930-79d3ef2afe87" to "School Life",
        "87cc87cd-a395-47af-b27a-93258283bbc6" to "Adventure",
        "e5301a23-ebd9-49dd-a0cb-2add944c7fe9" to "Slice of Life",
        "d14322ac-4d6f-4e9b-afd9-629d5f4d8a41" to "Villainess",
        "eabc5b4c-6aff-42f3-b657-3e90cbd00b75" to "Supernatural",
        "a1f53773-c69a-4ce5-8cab-fffcd90b1565" to "Magic",
        "ee968100-4191-4968-93d3-f82d72be7e46" to "Mystery",
        "0234a31e-a729-4e28-9d6a-3f87c4966b9e" to "Oneshot",
        "256c8bd9-4904-4360-bf4f-508a76d67183" to "Sci-Fi",
        "cdad7e68-1419-41dd-bdce-27753074a640" to "Horror",
        "799c202e-7daa-44eb-9cf7-8a3c0441531e" to "Martial Arts",
        "69964a64-2f90-4d33-beeb-f3ed2875eb4c" to "Sports"
    )
}