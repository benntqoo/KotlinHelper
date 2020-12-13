package com.jrtou.kotlinhelper.helper

import android.content.Context
import android.graphics.Bitmap
import android.os.Environment
import android.os.StatFs
import android.util.Log
import java.io.File
import java.io.FileOutputStream

object FileHelper {
    private const val TAG = "FileHelper"

    /**
     * 取得下載存放位置<br></br>
     * 有 storage 存放在 storage/emulated/0/Android/data/package name/cache/
     * 無 storage 存放在 data/data/package name/cache
     *
     * @param context     context
     * @param fileName package name
     * @return download path
     */
    fun getDownloadPath(context: Context, fileName: String): String = getDiskCacheDir(context) + File.separator + fileName


    private fun getLocalSavePath(context: Context, dirName: String): String? = context.getDir(dirName, Context.MODE_WORLD_READABLE).absolutePath

    fun getSaveImgPath(context: Context, packageName: String): String? = getLocalSavePath(context, "image") + File.separator + packageName + ".png"

    fun getDiskCacheDir(context: Context): String? {
        var cachePath: String? = null
        if ((Environment.MEDIA_MOUNTED == Environment.getExternalStorageState() || !Environment.isExternalStorageRemovable()) && context.externalCacheDir != null) {
            cachePath = context.externalCacheDir!!.path
            if (cachePath == null) cachePath = getLocalSavePath(context, "download") //大盒子用
        } else cachePath = context.cacheDir.path
        return cachePath
    }

    fun saveBitmap(context: Context, bm: Bitmap, saveName: String): Boolean {
        getSaveImgPath(context, saveName)?.let { saveImgPath ->
            val bitmapFile = File(saveImgPath)

            if (!bitmapFile.exists()) bitmapFile.createNewFile()
            try {
                FileOutputStream(bitmapFile).use { fileOutputStream ->
                    bm.compress(Bitmap.CompressFormat.PNG, 90, fileOutputStream)
                    fileOutputStream.flush()
                    Log.i(TAG, "save file success:$saveName")
                    return true
                }
            } catch (e: Exception) {
                Log.e(TAG, "saveBitmap: ", e)
                return false
            }
        } ?: return false
    }

    fun getSysSize(): Long {
        val path = Environment.getDataDirectory()
        val stat = StatFs(path.path)
        val blockSize = stat.blockSize.toLong()
        val availableBlocks = stat.availableBlocks.toLong()
        return Math.max(blockSize * availableBlocks - 250 * 1024 * 1024, 0)
    }

    fun getSDSize(): Long {
        val path = Environment.getExternalStorageDirectory()
        val stat = StatFs(path.path)
        val blockSize = stat.blockSize.toLong()
        val availableBlocks = stat.availableBlocks.toLong()
        return blockSize * availableBlocks
    }
}