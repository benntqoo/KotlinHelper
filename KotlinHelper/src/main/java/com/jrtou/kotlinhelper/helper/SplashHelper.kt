package com.jrtou.kotlinhelper.helper

import androidx.annotation.StringDef
import java.util.concurrent.TimeUnit

class SplashHelper(private var callback: SplashCallback) {
    companion object {
        private const val TAG = "SplashHelper"
        const val STOP = "1"
        const val NONE = "0"
        const val START_ACTIVITY = "-1"

        @Target(AnnotationTarget.TYPE, AnnotationTarget.PROPERTY)
        @StringDef(STOP, NONE, START_ACTIVITY)
        @Retention(AnnotationRetention.SOURCE)
        annotation class Status
    }

    private var mHandler: SplashHandler = SplashHandler()

    /**
     * 延遲跳轉時間
     */
    private var delayTime: Long = 3000
    /**
     * 單位 ms
     */
    private var unit: TimeUnit = TimeUnit.MILLISECONDS
    /**
     * 是否跳轉
     */
    var isSplash: Boolean = false

    /**
     * 當前  status
     */
    @Status
    private var status: String = NONE

    fun startDelay() {
        mHandler.postDelayed({
            if (!isSplash) {
                isSplash = true
                callback.onSplashIntent()
            }
        }, checkDuration(delayTime, unit))
    }


    fun setDelayTime(time: Long) {
        delayTime = time
    }

    fun setStatus(status: @Status String) {
        this.status = status
    }

    private fun checkDuration(duration: Long, unit: TimeUnit): Long {
        if (duration < 0) throw IllegalArgumentException("$TAG < 0")

        val millis = unit.toMillis(duration)
        if (millis > Integer.MAX_VALUE) throw IllegalArgumentException("$TAG too large.")
        if (millis == 0L && duration > 0) throw IllegalArgumentException("$TAG too small.")
        return millis
    }

    fun getStatus(): String {
        return status
    }

    class SplashHandler : android.os.Handler()
    /**
     * 跳轉回調
     */
    interface SplashCallback {
        fun onSplashIntent()
    }
}