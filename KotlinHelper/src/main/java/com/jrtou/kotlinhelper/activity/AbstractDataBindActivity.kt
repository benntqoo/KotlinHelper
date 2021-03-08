package com.jrtou.kotlinhelper.activity

import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class AbstractDataBindActivity<BIND : ViewDataBinding> : AbstractActivity() {
    companion object {
        private const val TAG = "BaseActivity"
    }

    lateinit var bind: BIND


    /**
     * 不跟隨系統改變字體大小
     */
    override fun disableFontSizeFollowSystem(): Boolean = true

    /**
     * 開啟 databinding
     */
    override fun enableDataBinding(): Boolean = true

    override fun initDataBinding(layoutRes: Int) {
        bind = DataBindingUtil.setContentView(this, layoutRes)
        bindViewModel(bind)
    }

    abstract fun bindViewModel(binding: BIND)
}