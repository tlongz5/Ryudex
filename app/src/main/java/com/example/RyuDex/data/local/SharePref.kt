package com.example.RyuDex.data.local

import android.content.Context
import com.example.RyuDex.utils.Constant.SEARCH_HISTORY

fun saveSearchHistory(context: Context, keyword:String){

    val searchHistory = context.getSharedPreferences(SEARCH_HISTORY, Context.MODE_PRIVATE)
    val editor = searchHistory.edit()

    val searchList = searchHistory.getString(SEARCH_HISTORY,"")!!
        .split(",").toMutableList()

    searchList.remove(keyword)
    searchList.add(0,keyword)
    editor.putString(SEARCH_HISTORY, searchList.take(20).joinToString(","))
    editor.apply()

}

fun getSearchHistory(context: Context): List<String>{
    val pref = context.getSharedPreferences(SEARCH_HISTORY, Context.MODE_PRIVATE)
    val searchHistory = pref.getString(SEARCH_HISTORY, "")!!
    if (searchHistory.isEmpty()) return emptyList()
    return searchHistory.split(",").toList()
}

fun clearSearchHistory(context: Context){
    val pref = context.getSharedPreferences(SEARCH_HISTORY, Context.MODE_PRIVATE)
    val editor = pref.edit()
    editor.putString(SEARCH_HISTORY, "").apply()
}