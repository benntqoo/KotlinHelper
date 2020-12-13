package com.jrtou.kotlinhelper.helper

import java.io.BufferedReader
import java.io.InputStreamReader

object RunTimeHelper {
    private const val TAG = "RunTimeHelper"
    const val COMMAND_SPLIT = " "

    fun getShellResult(cmd: String): MutableList<String> {
        return Runtime.getRuntime()?.exec(cmd)?.run process@{
            InputStreamReader(inputStream).use { inputStreamReader ->
                BufferedReader(inputStreamReader).useLines {
                    it.toMutableList()
                }
            }
        } ?: mutableListOf()
    }
}