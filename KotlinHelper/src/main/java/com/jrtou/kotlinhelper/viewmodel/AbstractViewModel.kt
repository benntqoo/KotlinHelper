package com.jrtou.kotlinhelper.viewmodel

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

    val isLoading: MutableLiveData<Event<Boolean>> = MutableLiveData()

    val errorMessage: MutableLiveData<Event<String>> = MutableLiveData()

    fun <T> showLoading(api: Resource<T>?) {
        isLoading.value = Event((api?.status ?: Status.LOADING) == Status.LOADING)
    }
}