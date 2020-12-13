package com.jrtou.kotlinhelper.helper

import android.graphics.Color
import android.text.TextUtils
import android.util.Log
import androidx.annotation.ColorRes

object ColorHelper {
    private const val TAG = "ColorHelper"

    fun stringToInt(colorStr: String): Int {
        if (TextUtils.isEmpty(colorStr)) return android.R.color.white

        return if (colorStr[0] == '#') {
            when (colorStr.length) {
                7 -> {
                    try {
                        Color.parseColor(colorStr)
                    } catch (e: Exception) {
                        Log.e(TAG, "colorCodeToInt: ", e)
                        android.R.color.white
                    }
                }
                9 -> {
                    val hexColor = colorStr.substring(1)
                    (hexColor.substring(0, 2).toInt(16) shl 24) + hexColor.substring(2).toInt(16)
                }
                else -> android.R.color.white
            }
        } else stringToInt("#$colorStr")
    }

    fun stringToInt(colorStr: String, @ColorRes defaultColor: Int): Int {
        if (TextUtils.isEmpty(colorStr)) return defaultColor

        return if (colorStr[0] == '#') {
            when (colorStr.length) {
                7 -> {
                    try {
                        Color.parseColor(colorStr)
                    } catch (e: Exception) {
                        Log.e(TAG, "colorCodeToInt: ", e)
                        defaultColor
                    }
                }
                9 -> {
                    val hexColor = colorStr.substring(1)
                    (hexColor.substring(0, 2).toInt(16) shl 24) + hexColor.substring(2).toInt(16)
                }
                else -> defaultColor
            }
        } else stringToInt("#$colorStr", defaultColor)
    }

    fun stringToInt(colorStr: String, defaultColor: String): Int {
        if (TextUtils.isEmpty(colorStr)) return stringToInt(defaultColor)
        return if (colorStr[0] == '#') {
            when (colorStr.length) {
                7 -> {
                    try {
                        Color.parseColor(colorStr)
                    } catch (e: Exception) {
                        Log.e(TAG, "colorCodeToInt: ", e)
                        stringToInt(defaultColor)
                    }
                }
                9 -> {
                    val hexColor = colorStr.substring(1)
                    (hexColor.substring(0, 2).toInt(16) shl 24) + hexColor.substring(2).toInt(16)
                }
                else -> stringToInt(defaultColor)
            }
        } else stringToInt("#$colorStr", defaultColor)
    }
}