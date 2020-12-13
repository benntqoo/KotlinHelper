package com.jrtou.kotlinhelper.helper

import android.util.Log
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset

object EncodeHelper {
    private const val TAG = "EncodeHelper"

    /**
     * big5 to unicode
     *
     * @param encodeStr [String]
     * @return unicode [String]
     */
    fun encode(encodeStr: String): String? {
        var strReturn = ""
        try {
            strReturn = String(encodeStr.toByteArray(charset("big5")), Charsets.UTF_8)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return strReturn
    }

    /**
     * unicode to big5
     *
     * @param decodeStr [String]
     * @return big5 [String]
     */
    fun decode(decodeStr: String): String? {
        var strReturn = ""
        try {
            strReturn = String(decodeStr.toByteArray(charset("UTF-8")), Charset.forName("big5"))
        } catch (e: java.lang.Exception) {
            Log.e(EncodeHelper.TAG, "decode: ", e)
            e.printStackTrace()
        }
        return strReturn
    }


    fun toBig5(strings: String): String? {
        return try {
            String(strings.toByteArray(), Charset.forName("big5"))
        } catch (e: UnsupportedEncodingException) {
            Log.e(TAG, "toBig5: ", e)
            e.printStackTrace()
            ""
        }
    }
}