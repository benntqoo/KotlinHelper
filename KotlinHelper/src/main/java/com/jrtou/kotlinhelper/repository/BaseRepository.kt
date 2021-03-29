package com.jrtou.kotlinhelper.repository

import android.os.Looper
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.jrtou.kotlinhelper.api.Resource
import com.jrtou.kotlinhelper.api.Status
import com.jrtou.kotlinhelper.livedata.Event
import com.jrtou.kotlinhelper.viewmodel.AbstractViewModel

open class BaseRepository {
    /**
     * API 使用 加仔判斷標記
     */
    val isLoading: MutableLiveData<Event<Boolean>> = MutableLiveData(Event(false))
    val message: MutableLiveData<Event<String>> = MutableLiveData()

    /**
     * 與 viewModel 進行關聯 加載 與 錯誤訊息
     */
    fun setupWithViewModel(vm: AbstractViewModel) {
        vm.isLoading.addSource(isLoading) {
            it?.getDataIfNotHandled()?.apply {
                if (Looper.myLooper() == Looper.getMainLooper()) vm.isLoading.value = Event(this)
                else vm.isLoading.postValue(Event(this))
            }
        }

        vm.message.addSource(message) {
            it?.getDataIfNotHandled()?.apply {
                if (this.isEmpty()) return@addSource
                if (Looper.myLooper() == Looper.getMainLooper()) vm.message.value = Event(this)
                else vm.message.postValue(Event(this))
            }
        }
    }

    fun unbindViewModel(vm: AbstractViewModel) {
        vm.isLoading.removeSource(isLoading)
        vm.message.removeSource(message)
    }

    /**
     * 讓 [AbstractViewModel][com.jrtou.kotlinhelper.viewmodel.AbstractViewModel]
     * 監聽統一使用
     */
    fun addLoadingSource(viewModelLoading: MediatorLiveData<Event<Boolean>>) {
        viewModelLoading.addSource(isLoading) {
            it?.getDataIfNotHandled()?.apply {

                if (Looper.myLooper() == Looper.getMainLooper()) viewModelLoading.value = Event(this)
                else viewModelLoading.postValue(Event(this))
            }
        }
    }

    /**
     * 判斷 api resource 是否需要顯示 加載UI
     */
    fun <T> showLoading(response: Resource<T>?) {
        response ?: let {
            if (Looper.myLooper() == Looper.getMainLooper()) isLoading.value = Event(false)
            else isLoading.postValue(Event(false))
            return
        }

        if (Looper.myLooper() == Looper.getMainLooper()) isLoading.value = Event(response.status == Status.LOADING)
        else isLoading.postValue(Event(response.status == Status.LOADING))
    }
}