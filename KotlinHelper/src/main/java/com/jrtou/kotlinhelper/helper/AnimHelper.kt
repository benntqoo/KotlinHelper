package com.jrtou.kotlinhelper.helper

import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.annotation.AnimRes
import com.jrtou.kotlinhelper.R

/**
 * @author Ben
 */
object AnimHelper {
    fun shakeUI(
        view: View,
        @AnimRes anim: Int = R.anim.anim_shake,
        repeatCount: Int = 5,
        repeatMode: Int = Animation.REVERSE
    ) {
        val shake = AnimationUtils.loadAnimation(view.context, anim)
        shake.repeatCount = repeatCount
        shake.fillBefore = true
        shake.repeatMode = repeatMode
        view.startAnimation(shake)
    }
}