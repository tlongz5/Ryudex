package com.example.RyuDex.utils

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.View
import com.google.android.material.chip.Chip

object ChipFactory {
    fun createChip(context: Context, text: String): Chip {
        return Chip(context).apply {
            this.text = text

            isCheckable = true
            isClickable = true

            textSize = 16f

            chipCornerRadius = 12f
            chipStrokeWidth = 1f

            setTextColor(Color.parseColor("#202020"))

            chipStrokeColor =
                ColorStateList.valueOf(Color.parseColor("#8C7B82"))

            chipBackgroundColor = ColorStateList(
                arrayOf(
                    intArrayOf(android.R.attr.state_checked),
                    intArrayOf()
                ),
                intArrayOf(
                    Color.parseColor("#D81B60"), // checked
                    Color.parseColor("#F8DCE7")  // unchecked
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
                        Color.parseColor("#202020")
                    )
                )
            )

            rippleColor = ColorStateList.valueOf(Color.parseColor("#33D81B60"))

            setEnsureMinTouchTargetSize(false)
        }
    }
}