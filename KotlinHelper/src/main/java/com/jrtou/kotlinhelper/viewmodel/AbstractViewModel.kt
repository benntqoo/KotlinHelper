package com.jrtou.kotlinhelper.viewmodel

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jrtou.kotlinhelper.api.Resource
import com.jrtou.kotlinhelper.api.Status
import com.jrtou.kotlinhelper.livedata.Event

/**
 * 攜帶 isLoading 配合 api loading 使用
 */
abstract class AbstractViewModel() : ViewModel() {
    companion object {
        private const val TAG = "AbstractViewModel"
    }

    /**
     * 加載監聽
     */
    val isLoading: MediatorLiveData<Event<Boolean>> = MediatorLiveData()

    /**
     * 訊息提示
     */
    val message: MediatorLiveData<Event<String>> = MediatorLiveData()


    abstract fun setupWithRepository()
    abstract fun unbindRepository()


    fun <T> showLoading(api: Resource<T>?) {
        isLoading.value = Event((api?.status ?: Status.LOADING) == Status.LOADING)
    }

    override fun onCleared() {
        super.onCleared()
        unbindRepository()
    }
}