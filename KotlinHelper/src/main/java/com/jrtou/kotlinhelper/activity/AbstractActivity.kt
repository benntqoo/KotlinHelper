package com.jrtou.kotlinhelper.activity

import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.jrtou.kotlinhelper.exception.OverrideException

abstract class AbstractActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "AbstractActivity"
    }

    @LayoutRes
    abstract fun getLayoutRes(): Int
    abstract fun onRestoreData(bundle: Bundle)
    abstract fun onSaveDate(bundle: Bundle)

    /**
     * 生命週期 [onCreate]
     *
     * 是否開啟　data binging
     *
     * 為 true 需 override [initDataBinding]
     */
    abstract fun enableDataBinding(): Boolean

    /**
     * 生命週期 [onCreate]
     *
     * after [initDataBinding]
     */
    abstract fun onViewSetting()

    /**
     * 生命週期 [onResume]
     *
     * 綁定數據源
     */
    abstract fun bindData()

    /**
     * 生命週期 [onResume]
     *
     * 在 [bindData] 之後執行
     */
    abstract fun onRender()

    /**
     * 生命週期 [onPause]
     *
     * 解綁數據源
     */
    abstract fun unbindData()

    /**
     * 是否開啟 loading ui
     */
    abstract fun isLoading(isLoading: Boolean)

    /**
     * 是否顯示 message dialog
     */
    abstract fun showMessage(message: String, submit: (() -> Unit)? = null, cancel: (() -> Unit)? = null)
    fun showMessage(@StringRes message: Int, submit: (() -> Unit)? = null, cancel: (() -> Unit)? = null) = showMessage(getString(message), submit, cancel)


    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.extras?.let { onRestoreData(it) }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        onRestoreData(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        onSaveDate(outState)
        super.onSaveInstanceState(outState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (enableDataBinding()) initDataBinding(getLayoutRes())
        else setContentView(getLayoutRes())
        onViewSetting()
        intent?.extras?.let { onRestoreData(it) } ?: savedInstanceState?.let { onRestoreData(it) }
    }

    override fun onResume() {
        super.onResume()
        bindData()
        onRender()
    }

    override fun onPause() {
        super.onPause()
        unbindData()
    }

    override fun getResources(): Resources {
        var res = super.getResources()
        if (disableFontSizeFollowSystem()) {
            val newConfig = Configuration()
            newConfig.setToDefaults()
            newConfig.fontScale = 1f
            res = createConfigurationContext(newConfig).resources
        }
        return res
    }


    fun showToast(@StringRes message: Int) = showToast(getString(message))
    fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    /**
     * [enableDataBinding] 為 true 需 override
     */
    open fun initDataBinding(@LayoutRes layoutRes: Int) {
        throw OverrideException("initDataBinding")
    }

    /**
     * sp 數值是否跟隨系統異動
     */
    open fun disableFontSizeFollowSystem(): Boolean = false


    fun createSnackBar(view: View, @StringRes message: Int, time: Int = 2500) = createSnackBar(view, getString(message), time)
    fun createSnackBar(view: View, message: String, time: Int = 2500) = Snackbar.make(view, message, time)
    fun showSnackBar(view: View, @StringRes message: Int, isFocus: Boolean, time: Int = 2500) = showSnackBar(view, getString(message), isFocus, time)
    fun showSnackBar(view: View, message: String, isFocus: Boolean, time: Int = 2500) {
        if (isFocus) view.requestFocus()
        createSnackBar(view, message, time).show()
    }
}