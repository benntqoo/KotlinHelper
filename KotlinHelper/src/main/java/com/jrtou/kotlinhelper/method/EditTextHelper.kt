package com.jrtou.kotlinhelper.method

import android.text.InputFilter
import android.widget.EditText

fun EditText.removeSpaceAndLine() {
    /**
     * 移除空白與換行
     */
    val editFilter = InputFilter { source, _, _, _, _, _ ->
        if (source == " " || source.toString().contentEquals("\n")) ""
        else null
    }
    this.filters = arrayOf(editFilter)
}