package com.example.RyuDex.utils

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.graphics.Color
import android.view.View

fun View.startPulseAnim(): ObjectAnimator{
    val anim =  ObjectAnimator.ofObject(
        this,
        "backgroundColor",
        ArgbEvaluator(),
        Color.WHITE,
        Color.parseColor("#FF0000")
    ).apply {
        duration = 1000
        repeatCount = ObjectAnimator.INFINITE
        repeatMode = ObjectAnimator.REVERSE
        start()
    }

    this.addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
        override fun onViewAttachedToWindow(v: View) {
        }

        override fun onViewDetachedFromWindow(v: View) {
            anim.cancel()
            v.removeOnAttachStateChangeListener(this)
        }
    })
    return anim
}