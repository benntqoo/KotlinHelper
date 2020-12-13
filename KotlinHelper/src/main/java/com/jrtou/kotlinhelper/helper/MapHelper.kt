package com.jrtou.kotlinhelper.helper

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.content.pm.PackageManager


class MapHelper {
    companion object {
        private const val TAG = "MapHelper"

        private const val GOOGLE_MAP_PACKAGE_NAME = "com.google.android.apps.maps"

        fun intentToMapByAddress(activity: Activity, location: String) {
            val gmmIntentUri = Uri.parse("geo:0,0?q=1600 $location")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            if (checkGoogleMapExit(activity)) mapIntent.setPackage(GOOGLE_MAP_PACKAGE_NAME)
            activity.startActivity(mapIntent)
        }

        fun intentToMapByGoogleUrl(activity: Activity, url: String) {
            if (checkGoogleMapExit(activity)) {
                try {
                    val gmmIntentUri = Uri.parse(url)
                    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                    mapIntent.setPackage(GOOGLE_MAP_PACKAGE_NAME)
                    activity.startActivity(mapIntent)
                } catch (e: Exception) {
                    Log.e(TAG, "intentToMapByGoogleUrl: ", e)
                }
            } else {
                try {
                    val intentWeb = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    activity.startActivity(intentWeb)
                } catch (e: java.lang.Exception) {

                    Log.e(TAG, "intentToMapByGoogleUrl: ", e)
                }
            }
        }

        fun checkGoogleMapExit(activity: Activity): Boolean {
            val pm = activity.packageManager
            try {
                pm.getPackageInfo(GOOGLE_MAP_PACKAGE_NAME, PackageManager.GET_ACTIVITIES)
                return true
            } catch (e: PackageManager.NameNotFoundException) {
                Log.e(TAG, "checkGoogleMapExit (line 35): ", e)
            }
            return false
        }
    }
}