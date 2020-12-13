package com.jrtou.kotlinhelper.helper

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast

object CopyTextHelper {
    fun copy(context: Context, text: String) {
        val myClipboard: ClipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val myClip = ClipData.newPlainText("text", text)
        myClipboard.setPrimaryClip(myClip)
        Toast.makeText(context, "複製成功", Toast.LENGTH_SHORT).show()
    }
}