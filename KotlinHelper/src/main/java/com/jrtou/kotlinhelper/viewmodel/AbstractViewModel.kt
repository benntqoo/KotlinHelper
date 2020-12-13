package com.jrtou.kotlinhelper.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jrtou.kotlinhelper.api.Resource
import com.jrtou.kotlinhelper.api.Status

/**
 * 攜帶 isLoading 配合 api loading 使用
 */
abstract class AbstractViewModel() : ViewModel() {
    companion object {
        private const val TAG = "AbstractViewModel"
    }

    val isLoading: MutableLiveData<Boolean> = MutableLiveData()

    val errorMessage: MutableLiveData<String> = MutableLiveData()

    fun <T> showLoading(api: Resource<T>?) {
        isLoading.value = (api?.status ?: Status.LOADING) == Status.LOADING
    }

    /**
     * 清除 錯誤訊息
     */
    fun clearMessage() {
        errorMessage.value = ""
    }
}