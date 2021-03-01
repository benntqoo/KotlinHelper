package com.jrtou.kotlinhelper.fragment

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.jrtou.kotlinhelper.extend.reuseRoot

abstract class AbstractDataBingFragment<A : AppCompatActivity, BIND : ViewDataBinding> : Fragment() {
    companion object {
        private const val TAG = "AbstractFragment2"
    }


    lateinit var bind: BIND
    var mActivity: A? = null


    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        mActivity?.let { if (hidden) onHidden(it) else if (isResumed) onShow(it) }
    }


    /**
     * 插入 view
     */
    open fun onInflaterView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View = if (this::bind.isInitialized) bind.reuseRoot()
    else {
        bind = DataBindingUtil.inflate(inflater, getLayoutRes(), container, false)
        initDataBind(bind)
        bind.root
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = onInflaterView(inflater, container, savedInstanceState)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        savedInstanceState?.let { onRestoreState(it) } ?: arguments?.let { onRestoreState(it) }
        activity?.let {
            mActivity = it as A
            onViewSetting(it)
            bindObserver(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        unbindObserver()
        mActivity = null
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        onSaveState(outState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        savedInstanceState?.let { onRestoreState(it) }
    }

    override fun onResume() {
        super.onResume()
        mActivity?.let { if (!isHidden) onRenderView(it) }
    }

    /**
     * 判斷使否有權限
     */
    fun checkPermission(permission: String): Boolean = activity?.run { ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED } ?: false


    @LayoutRes
    abstract fun getLayoutRes(): Int

    abstract fun initDataBind(bind: BIND)

    /**
     * fragment 重新建立恢復 bundle 處存資料
     */
    abstract fun onRestoreState(savedInstanceState: Bundle)

    /**
     * fragment 保存狀態時觸發 以保存 show hide 判斷在建立時候 判斷 show/hide
     */
    abstract fun onSaveState(outState: Bundle)

    /**
     * 綁定監聽數據
     */
    abstract fun bindObserver(activity: A)

    /**
     * 解除 Observer 綁定
     */
    abstract fun unbindObserver()

    /**
     * 初始化 ui 基本設定 ex setListener or setAdapter
     */
    abstract fun onViewSetting(activity: A)

    /**
     *請求數據 渲染至 ui 上
     */
    abstract fun onRenderView(activity: A)

    /**
     * activity 存在時 fragment 顯示
     */
    abstract fun onShow(activity: A)

    /**
     * activity 存在時 fragment 隱藏
     */
    abstract fun onHidden(activity: A)
}