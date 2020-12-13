package com.jrtou.kotlinhelper.helper

import android.Manifest.permission.WRITE_SETTINGS
import android.app.Activity
import android.app.KeyguardManager
import android.content.Context
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Point
import android.os.Build
import android.provider.Settings
import android.util.DisplayMetrics
import android.view.*
import androidx.annotation.NonNull
import androidx.annotation.RequiresPermission

object ScreenHelper {
    private val TAG: String = ScreenHelper::class.java.simpleName
    fun dp2px(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    /**
     * Return the width of screen, in pixel.
     *
     * @return the width of screen, in pixel
     */
    fun getScreenWidth(@NonNull activity: Activity): Int {
        val windowManager: WindowManager = activity.application.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val point = Point()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) windowManager.defaultDisplay.getRealSize(point)
        else windowManager.defaultDisplay.getSize(point)
        return point.x
    }

    /**
     * Return the width of screen, in pixel.
     *
     * @return the width of screen, in pixel
     */
    fun getScreenWidth(@NonNull activity: Context): Int {
        val windowManager: WindowManager = activity.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val point = Point()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) windowManager.defaultDisplay.getRealSize(point)
        else windowManager.defaultDisplay.getSize(point)
        return point.x
    }

    /**
     * Return the height of screen, in pixel.
     *
     * @return the height of screen, in pixel
     */

    fun getScreenHeight(@NonNull activity: Activity): Int {
        val windowManager: WindowManager = activity.application.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val point: Point = Point()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) windowManager.defaultDisplay.getRealSize(point)
        else windowManager.defaultDisplay.getSize(point)
        return point.y
    }

    /**
     * Return the density of screen.
     *
     * @return the density of screen
     */
    fun getScreenDensity(): Float {
        return Resources.getSystem().displayMetrics.density
    }

    /**
     * Return the screen density expressed as dots-per-inch.
     *
     * @return the screen density expressed as dots-per-inch
     */
    fun getScreenDensityDpi(): Int {
        return Resources.getSystem().displayMetrics.densityDpi
    }

    /**
     * Set full screen.
     *
     * @param activity The activity.
     * @param isFullScreen Set full screen or  non full screen.
     */
    fun setFullScreen(@NonNull activity: Activity, isFullScreen: Boolean) {
        if (isFullScreen) activity.window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        else activity.window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }


    /**
     * Toggle full screen.
     *
     * @param activity The activity.
     */
    fun toggleFullScreen(@NonNull activity: Activity) {
        val fullScreenFlag: Int = WindowManager.LayoutParams.FLAG_FULLSCREEN
        val window: Window = activity.window

        if ((window.attributes.flags and fullScreenFlag) == fullScreenFlag) window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN or WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        else window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN or WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
    }

    /**
     * Return whether screen is full.
     *
     * @param activity The activity.
     * @return {@code true}: yes<br>{@code false}: no
     */
    fun isFullScreen(@NonNull activity: Activity): Boolean {
        return (activity.window.attributes.flags and WindowManager.LayoutParams.FLAG_FULLSCREEN) == WindowManager.LayoutParams.FLAG_FULLSCREEN
    }

    /**
     * Set the screen to landscape.
     *
     * @param activity The activity.
     */
    fun setLandscape(@NonNull activity: Activity) {
        activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
    }

    /**
     * Set the screen to portrait.
     *
     * @param activity The activity.
     */
    fun setPortrait(@NonNull activity: Activity) {
        activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    /**
     * Return whether screen is landscape.
     *
     * @return {@code true}: yes<br>{@code false}: no
     */
    fun isLandscape(@NonNull activity: Activity): Boolean {
        return activity.application.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    }

    /**
     * Return whether screen is portrait.
     *
     * @return {@code true}: yes<br>{@code false}: no
     */
    fun isPortrait(@NonNull activity: Activity): Boolean {
        return activity.application.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT
    }

    /**
     * Return the rotation of screen.
     *
     * @param activity The activity.
     * @return the rotation of screen
     */
    fun getScreenRotation(@NonNull activity: Activity): Int {
        return when (activity.windowManager.defaultDisplay.rotation) {
            Surface.ROTATION_0 -> 0
            Surface.ROTATION_90 -> 90
            Surface.ROTATION_180 -> 180
            Surface.ROTATION_270 -> 270
            else -> 0
        }
    }

    /**
     * Return the bitmap of screen.
     *
     * @param activity The activity.
     * @return the bitmap of screen
     */
    fun screenShot(@NonNull activity: Activity): Bitmap? {
        return screenShot(activity, false)
    }

    /**
     * Return the bitmap of screen.
     *
     * @param activity          The activity.
     * @param isDeleteStatusBar True to delete status bar, false otherwise.
     * @return the bitmap of screen
     */
    fun screenShot(@NonNull activity: Activity, isDeleteStatusBar: Boolean): Bitmap? {
        val decorView = activity.window.decorView
        decorView.isDrawingCacheEnabled = true
        decorView.setWillNotCacheDrawing(false)

        val bmp: Bitmap? = decorView.drawingCache ?: return null

        val dm = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(dm)

        val ret = if (isDeleteStatusBar) {
            val resources = activity.resources
            val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
            val statusBarHeight = resources.getDimensionPixelSize(resourceId)
            Bitmap.createBitmap(bmp!!, 0, statusBarHeight, dm.widthPixels, dm.heightPixels - statusBarHeight)
        } else Bitmap.createBitmap(bmp!!, 0, 0, dm.widthPixels, dm.heightPixels)

        decorView.destroyDrawingCache()
        return ret
    }

    /**
     * Return whether screen is locked.
     *
     * @return {@code true}: yes<br>{@code false}: no
     */
    fun isScreenLock(@NonNull activity: Activity): Boolean {
        val km: KeyguardManager = activity.application.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
        return km.inKeyguardRestrictedInputMode()
    }

    /**
     * Set the duration of sleep.
     * <p>Must hold {@code <uses-permission android:name="android.permission.WRITE_SETTINGS" />}</p>
     *
     * @param duration The duration.
     */
    @RequiresPermission(WRITE_SETTINGS)
    fun setSleepDuration(@NonNull activity: Activity, duration: Int) {
        Settings.System.putInt(activity.application.contentResolver, Settings.System.SCREEN_OFF_TIMEOUT, duration)
    }

    /**
     * Return the duration of sleep.
     *
     * @return the duration of sleep.
     */
    fun getSleepDuration(@NonNull activity: Activity): Int {
        return try {
            Settings.System.getInt(activity.application.contentResolver, Settings.System.SCREEN_OFF_TIMEOUT)
        } catch (e: Settings.SettingNotFoundException) {
            e.printStackTrace()
            -123
        }
    }

    /**
     * Return whether device is tablet.
     *
     * @return {@code true}: yes<br>{@code false}: no
     */
    fun isTablet(@NonNull activity: Activity): Boolean {
        return (activity.application.resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE
    }


    /**
     * 獲取狀態欄高度
     */
    fun getStatusBarHeight(): Int {
        val resources = Resources.getSystem()
        val statusBarId = resources.getIdentifier("status_bar_height", "dimen", "android")
        return resources.getDimensionPixelSize(statusBarId)
    }

    /**
     * 獲取 navigation bar 高度
     */
    fun getNavigationBarHeight(): Int {
        val resources = Resources.getSystem()
        val navigationBarId = resources.getIdentifier("navigation_bar_height", "dimen", "android")
        return resources.getDimensionPixelSize(navigationBarId)
    }

    /**
     * navigation bar 是否顯示
     */
    fun isNavigationBarShow(activity: Activity): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            val display = activity.windowManager.defaultDisplay
            val size = Point()
            val realSize = Point()
            display.getSize(size)
            display.getRealSize(realSize)
            return realSize.y != size.y
        } else {
            val menu = ViewConfiguration.get(activity).hasPermanentMenuKey()
            val back = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK)
            return !(menu || back)
        }
    }
}