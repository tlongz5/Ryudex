package com.example.RyuDex.utils

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.View
import com.google.android.material.chip.Chip

object ChipFactory {
    private const val BLUE = "#5D99EF"
    private const val BLUE_LIGHT = "#EAF3FF"
    private const val BLUE_STROKE = "#A9C9F5"
    private const val TEXT_DARK = "#1E3A5F"
    fun createChip(context: Context, text: String): Chip {
        return Chip(context).apply {
            this.text = text

            isCheckable = true
            isClickable = true

            textSize = 16f

            chipCornerRadius = 12f
            chipStrokeWidth = 1f

            setTextColor(Color.parseColor(TEXT_DARK))

            chipStrokeColor =
                ColorStateList.valueOf(Color.parseColor(BLUE_STROKE))

            chipBackgroundColor = ColorStateList(
                arrayOf(
                    intArrayOf(android.R.attr.state_checked),
                    intArrayOf()
                ),
                intArrayOf(
                    Color.parseColor(BLUE), // checked
                    Color.parseColor(BLUE_LIGHT)  // unchecked
                )
            )

            setTextColor(
                ColorStateList(
                    arrayOf(
                        intArrayOf(android.R.attr.state_checked),
                        intArrayOf()
                    ),
                    intArrayOf(
                        Color.WHITE,
                        Color.parseColor(TEXT_DARK)
                    )
                )
            )

            rippleColor = ColorStateList.valueOf(Color.parseColor("#334A90E2"))

            setEnsureMinTouchTargetSize(false)
        }
    }
}