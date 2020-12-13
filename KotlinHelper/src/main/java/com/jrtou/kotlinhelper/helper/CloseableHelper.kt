package com.jrtou.kotlinhelper.helper

import java.io.Closeable
import java.lang.Exception

object CloseableHelper {
    fun close(vararg io: Closeable?) {
        io.forEach {
            if (it == null) return@forEach
            try {
                it.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}