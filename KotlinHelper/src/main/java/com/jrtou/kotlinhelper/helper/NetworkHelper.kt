package com.jrtou.kotlinhelper.helper

import android.annotation.TargetApi
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
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

    @RequiresPermission(android.Manifest.permission.ACCESS_NETWORK_STATE)
    fun isWifi(context: Context?): Boolean {
        if (context == null) return false
        val connectivityManager = context.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val nw = connectivityManager.activeNetwork ?: return false
            val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
            when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true //for other device how are able to connect with Ethernet
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true//for check internet over Bluetooth
                else -> false
            }
        } else connectivityManager.activeNetworkInfo?.isConnected ?: false
    }
}