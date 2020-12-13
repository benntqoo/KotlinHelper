package com.jrtou.kotlinhelper.helper

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import android.util.Base64
import android.util.Log
import androidx.core.content.FileProvider
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception

object BaseImgHelper {
    const val TAG: String = "BaseImgHelper"

    fun getFileByKey(context: Context, key: String): File {
        return File(context.externalCacheDir, "$key.jpg")
    }

    /**
     * 大頭照 file 轉 bitmap
     *
     * @param file
     * @return
     */
    fun fileToBitmap(file: File): Bitmap? {
        return if (file.exists()) BitmapFactory.decodeFile(file.path)
        else null
    }

    /**
     * 剪裁暫存位置轉 bs64 string
     *
     * @return Base64 String
     */
    fun getBase64FromUri(cropUri: Uri?): String? {
        cropUri?.let {
            val bitmapDrawable: BitmapDrawable = BitmapDrawable.createFromPath(cropUri.path) as BitmapDrawable
            val bitmap = bitmapDrawable.bitmap
            return encode(bitmap)
        } ?: run { Log.e(TAG, "getBase64FromUri cropUri is null.") }
        return null
    }

    fun encode(bitmap: Bitmap, compressFormat: Bitmap.CompressFormat = Bitmap.CompressFormat.JPEG, quality: Int = 100): String {
        val byteArray = ByteArrayOutputStream()
        bitmap.compress(compressFormat, quality, byteArray)
        return Base64.encodeToString(byteArray.toByteArray(), Base64.NO_WRAP)
    }

    fun decode(base64: String?): Bitmap? {
        return base64?.run {
            val decode = Base64.decode(base64, Base64.DEFAULT)
            BitmapFactory.decodeByteArray(decode, 0, decode.size)
        }
    }

    fun saveBitmap(context: Context, key: String, bitmap: Bitmap) {
        try {
            val file = getFileByKey(context, key)
            val outStream = FileOutputStream(file.path)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outStream)
            outStream.close()
        } catch (e: Exception) {
            Log.e(TAG, "encodeToBase64 ", e)
        }
    }

    fun getBitmapUri(context: Context, key: String): Uri {
        val file = getFileByKey(context, key)
        return if (Build.VERSION.SDK_INT >= 24) FileProvider.getUriForFile(context, context.packageName, file)
        else Uri.fromFile(file)
    }

    fun getBitmap(context: Context, key: String): Bitmap? {
        val uri = getBitmapUri(context, key)
        return decode(getBase64FromUri(uri))
    }
}