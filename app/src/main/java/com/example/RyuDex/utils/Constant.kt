package com.example.RyuDex.utils

object Constant {
    const val BASE_URL = "https://api.mangadex.org/"  //api to get data

    // lấy thông tin tác giả và banner truyện
    val requires = listOf("author","cover_art")

    fun getCover(mangaId:String,fileName:String) : String{
        return "https://uploads.mangadex.org/covers/${mangaId}/${fileName}"
    }

    val POPULAR_TAGS = mapOf(
        "Hot" to null,
        "Action" to "391b0423-d847-456f-aff0-8b0cfc03066b",
        "Romance" to "423e2eae-a7a2-4a8b-ac03-a8351462d71d",
        "Web Comic" to "e197df38-d0e7-43b5-9b09-2842d0c326dd",
        "Comedy" to "4d32cc48-9f00-4cca-9b5a-a839f0764984",
        "Isekai" to "ace04997-f6bd-436e-b261-779182193d3d",
        "Fantasy" to "cdc58593-87dd-415e-bbc0-2ec27bf404cc",
        "Full Color" to "f5ba408b-0e7a-484d-8d49-4e9125ac96de",
        "School Life" to "caaa44eb-cd40-4177-b930-79d3ef2afe87",
        "Adventure" to "87cc87cd-a395-47af-b27a-93258283bbc6",
        "Slice of Life" to "e5301a23-ebd9-49dd-a0cb-2add944c7fe9",
        "Villainess" to "d14322ac-4d6f-4e9b-afd9-629d5f4d8a41",
        "Supernatural" to "eabc5b4c-6aff-42f3-b657-3e90cbd00b75",
        "Magic" to "a1f53773-c69a-4ce5-8cab-fffcd90b1565",
        "Mystery" to "ee968100-4191-4968-93d3-f82d72be7e46",
        "Oneshot" to "0234a31e-a729-4e28-9d6a-3f87c4966b9e",
        "Sci-Fi" to "256c8bd9-4904-4360-bf4f-508a76d67183",
        "Horror" to "cdad7e68-1419-41dd-bdce-27753074a640",
        "Martial Arts" to "799c202e-7daa-44eb-9cf7-8a3c0441531e",
        "Sports" to "69964a64-2f90-4d33-beeb-f3ed2875eb4c"
    )
}