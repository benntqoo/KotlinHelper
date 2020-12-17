package com.jrtou.kotlinhelper.repository


import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.jrtou.kotlinhelper.api.ApiErrorResponse
import com.jrtou.kotlinhelper.api.Resource

@Deprecated("1.0.3開始捨棄 改用 MediatorLiveData<Resource<T> 擴充方法")
abstract class AbstractRepository<T> {
    val result = MediatorLiveData<Resource<T>>()

    fun getLiveData() = result as LiveData<Resource<T>>
    fun setValue(value: Resource<T>) = apply { result.value = value }


    /**
     * 請求成功
     */
    fun setSuccess(data: T?) = apply { setValue(Resource.success(data)) }

    /**
     * 空數據
     */
    fun setEmptyValue() = apply { setValue(Resource.empty()) }
    fun loading(data: T? = null) = apply { setValue(Resource.loading(data)) }
    open fun setError(message: String?, data: T? = null) = apply { setValue(Resource.error(message ?: "unknown error.", data)) }

    open fun <D> setApiError(errorResponse: ApiErrorResponse<D>, data: T? = null) = apply {
        setError("Server response: ${errorResponse.code} ${errorResponse.throwable.message}", data)
    }

    open fun setUnknownError(data: T? = null) = apply { setValue(Resource.error("Server unknownServiceException", data)) }

    abstract fun setCustomError(message: String?, data: T? = null)
}