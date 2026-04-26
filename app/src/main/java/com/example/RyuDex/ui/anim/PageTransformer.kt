package com.example.RyuDex.ui.anim

import android.view.View
import androidx.viewpager2.widget.ViewPager2
import kotlin.math.abs

// anim cho banner
class PageTransformer: ViewPager2.PageTransformer {
    private val minScale = 0.8f // tỷ lệ nhỏ nhất của màn khác so với màn hình chính
    private val minAlpha = 0.7f // tỷ lệ nhỏ nhất của độ mờ khi ngoài màn chính
    override fun transformPage(page: View, position: Float) {
        // căn chỉnh view
        if(position>=-1 && position<=1){
            val scale = 1f- abs(position) *(1f- minScale)
            val alpha = 1f- abs(position) *(1f- minAlpha)
            page.scaleX = scale
            page.scaleY = scale
            page.alpha = alpha
        }
    }

}