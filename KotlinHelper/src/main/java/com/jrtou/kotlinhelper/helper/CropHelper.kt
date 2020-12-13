package com.jrtou.kotlinhelper.helper

import android.app.Activity
import android.net.Uri
import android.provider.MediaStore
import androidx.fragment.app.Fragment

object CropHelper {
    const val OPEN_ALUM: Int = 1233
    const val CROP_RESULT: Int = 1244

    /**
     * 打開相簿
     */
    fun choiceFromAlum(activity: Activity, requestCode: Int = OPEN_ALUM) {
        val choiceFromAlbumIntent = IntentHelper.getAlumIntent()
        activity.startActivityForResult(choiceFromAlbumIntent, requestCode)
    }

    fun choiceFromAlum(fragment: Fragment, requestCode: Int = OPEN_ALUM) {
        val choiceFromAlbumIntent = IntentHelper.getAlumIntent()
        fragment.startActivityForResult(choiceFromAlbumIntent, requestCode)
    }

    fun cropPhoto(activity: Activity, inputUri: Uri, finalPath: String, requestCode: Int = CROP_RESULT, width: Int = 300, height: Int = 300): Uri? {
        val cropPhotoIntent = IntentHelper.getCropIntent(inputUri, BaseImgHelper.getFileByKey(activity, finalPath), width, height)
        activity.startActivityForResult(cropPhotoIntent, requestCode)
        return cropPhotoIntent.getParcelableExtra(MediaStore.EXTRA_OUTPUT)
    }

    fun cropPhoto(activity: Activity, fragment: Fragment, inputUri: Uri, finalPath: String, requestCode: Int = CROP_RESULT, width: Int = 300, height: Int = 300): Uri? {
        val cropPhotoIntent = IntentHelper.getCropIntent(inputUri, BaseImgHelper.getFileByKey(activity, finalPath), width, height)
        fragment.startActivityForResult(cropPhotoIntent, requestCode)
        return cropPhotoIntent.getParcelableExtra(MediaStore.EXTRA_OUTPUT)
    }
}