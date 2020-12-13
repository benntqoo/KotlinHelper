package com.jrtou.kotlinhelper.helper

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.provider.Settings
import java.io.File
import java.io.IOException

object IntentHelper {

    /**
     * 打开系统图库的 Action，等同于: "android.intent.action.GET_CONTENT"
     */
    fun getAlumIntent(): Intent {
        val choiceFromAlbumIntent = Intent(Intent.ACTION_GET_CONTENT)
        choiceFromAlbumIntent.type = "image/*"      // 设置数据类型为图片类型
        return choiceFromAlbumIntent
    }

    /**
     * 打開剪裁 畫面
     * @param imgUri 需要剪裁圖片 uri
     * @param cropFile 剪裁後輸出位置
     * @param width 圖片寬度
     * @param height 圖片高度
     */
    fun getCropIntent(imgUri: Uri, cropFile: File, width: Int = 300, height: Int = 300): Intent {
        val cropPhotoIntent = Intent("com.android.camera.action.CROP")

        cropPhotoIntent.putExtra("outputX", width) // 裁剪宽
        cropPhotoIntent.putExtra("outputY", height)// 剪裁高
        cropPhotoIntent.setDataAndType(imgUri, "image/*") //設置數據類型 與 uri
        cropPhotoIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)  //讀取 uri 權限設置
        try {
            if (cropFile.exists()) cropFile.delete() // 檔案存在刪除檔案，並重新建立
            cropFile.createNewFile()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        val cropUri = Uri.fromFile(cropFile)
        return cropPhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, cropUri)
    }

    fun getShareIntent(title: String, subject: String, text: String, type: String = "text/plain"): Intent {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = type
        intent.putExtra(Intent.EXTRA_SUBJECT, subject)
        intent.putExtra(Intent.EXTRA_TEXT, text)
        return Intent.createChooser(intent, title)
    }

    fun getAppStoreShare(title: String, subject: String, packageName: String): Intent {
        return getShareIntent(title, subject, "https://play.google.com/store/apps/details?id=$packageName")
    }

    fun getAppStoreIntent(context: Context): Intent? {
        val uri = Uri.parse("market://details?id=" + context.packageName)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        return if (intent.resolveActivity(context.packageManager) != null) intent
        else {
            intent.data = Uri.parse("https://play.google.com/store/apps/details?id=" + context.packageName)
            if (intent.resolveActivity(context.packageManager) != null) intent
            else null
        }
    }

    fun getBrowser(url: String): Intent {
        return Intent(Intent.ACTION_VIEW).apply { data = Uri.parse(url) }
    }

    /**
     * APP 權限顯示不自提示後 跳轉 設置權限頁面
     */
    fun startToPermissionSettingIntent(context: Context) {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts("package", context.packageName, null))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    fun getMailIntent(context: Context, mail: String) {
        context.startActivity(
            Intent.createChooser(
                Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", mail, null))
                    .apply {
                        putExtra(Intent.EXTRA_SUBJECT, "UCA 意見")
                        putExtra(Intent.EXTRA_TEXT, "")
                    }, "UCA 意見"
            )
        )
    }
}