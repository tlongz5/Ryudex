package com.example.RyuDex.utils

import androidx.room.TypeConverter
import com.example.RyuDex.model.entity.DownloadStatus
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromPair(pair: Pair<String, String>): String {
        return gson.toJson(pair)
    }

    @TypeConverter
    fun toPair(json: String): Pair<String, String> {
        val type = object : TypeToken<Pair<String, String>>() {}.type
        return gson.fromJson(json, type)
    }

    @TypeConverter
    fun fromListPair(list: List<Pair<String, String>>): String {
        return gson.toJson(list)
    }

    @TypeConverter
    fun toListPair(json: String): List<Pair<String, String>> {
        val type = object : TypeToken<List<Pair<String, String>>>() {}.type
        return gson.fromJson(json, type)
    }

    @TypeConverter
    fun fromListString(list: List<String>): String {
        return gson.toJson(list)
    }

    @TypeConverter
    fun toListString(json: String): List<String> {
        val type = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(json, type)
    }

    @TypeConverter
    fun fromDownloadStatus(downloadStatus: DownloadStatus): String {
        return downloadStatus.name
    }

    @TypeConverter
    fun toDownloadStatus(status: String): DownloadStatus {
        return DownloadStatus.valueOf(status)
    }
}