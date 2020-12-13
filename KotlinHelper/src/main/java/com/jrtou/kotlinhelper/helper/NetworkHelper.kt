package com.jrtou.kotlinhelper.helper

import android.annotation.TargetApi
import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import androidx.annotation.RequiresPermission

/**
 * 需要适配到 android 9 以上
 */
object NetworkHelper {
    @RequiresPermission(android.Manifest.permission.ACCESS_NETWORK_STATE)
    @TargetApi(Build.VERSION_CODES.O_MR1)
    fun hasNetWork(context: Context): Boolean {
        val connectivityManager = context.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        val networkInfo = connectivityManager?.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}