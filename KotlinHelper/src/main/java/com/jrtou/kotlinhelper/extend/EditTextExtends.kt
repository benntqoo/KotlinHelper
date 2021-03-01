package com.jrtou.kotlinhelper.extend

import android.text.InputFilter
import android.text.InputType
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

fun EditText.inputFloat() {
    inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_SIGNED or InputType.TYPE_NUMBER_FLAG_DECIMAL
}

fun EditText.inputInt() {
    inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_SIGNED
}