package com.jrtou.kotlinhelper.method

import androidx.annotation.NonNull
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.jrtou.kotlinhelper.api.*

/**
 * 搭配 swiperefreshlayout
 */
fun <T> LiveData<Resource<T>>.observerApiWithSwipe(observer: Observer<Resource<T>>) {
    observeForever(object : Observer<Resource<T>> {
        override fun onChanged(response: Resource<T>?) {
            observer.onChanged(response)
            if (response?.status ?: Status.LOADING != Status.LOADING) removeObserver(this)
        }
    })
}

/**
 * api 請求依次監聽
 * @param observer [Observer]
 */
fun <T> LiveData<Resource<T>>.observerApi(observer: Observer<Resource<T>>) {
    observeForever(object : Observer<Resource<T>> {
        override fun onChanged(t: Resource<T>?) {
            observer.onChanged(t)
            if ((t?.status ?: Status.LOADING) != Status.LOADING) removeObserver(this)
        }
    })
}

/**
 * @param request api 請求事件
 * @param success api 請求成功 callback
 * @param empty api 請求數據為空 callback
 * @param error api 請求失敗 callback
 */
fun <I, T> MediatorLiveData<Resource<I>>.addApiSource(
    @NonNull request: LiveData<ApiResponse<T>>,
    success: (response: ApiSuccessResponse<T>) -> Unit,
    empty: (response: ApiEmptyResponse<T>) -> Unit,
    error: (response: ApiErrorResponse<T>) -> Unit
) {
    addSource(request) { response ->
        removeSource(request)
        when (response) {
            is ApiEmptyResponse -> empty(response)
            is ApiSuccessResponse -> success(response)
            is ApiErrorResponse -> error(response)
        }
    }
}

fun <T> MutableLiveData<T>.notifyObserver() {
    this.value = this.value
}

//////// API 使用
fun <T> MediatorLiveData<Resource<T>>.applyValue(value: Resource<T>) = apply { this.value = value }

/**
 * 請求成功
 */
fun <T> MediatorLiveData<Resource<T>>.applySuccess(data: T?) = apply { setValue(Resource.success(data)) }

/**
 * 空數據
 */
fun <T> MediatorLiveData<Resource<T>>.applyEmpty() = apply { setValue(Resource.empty()) }
fun <T> MediatorLiveData<Resource<T>>.applyLoading(data: T? = null) = apply { setValue(Resource.loading(data)) }
fun <T> MediatorLiveData<Resource<T>>.applyError(message: String?, data: T? = null) = apply { setValue(Resource.error(message ?: "unknown error.", data)) }

fun <D, T> MediatorLiveData<Resource<T>>.applyHttpError(errorResponse: ApiErrorResponse<D>, data: T? = null) = apply {
    applyError("Server response: ${errorResponse.code} ${errorResponse.throwable.message}", data)
}
////////