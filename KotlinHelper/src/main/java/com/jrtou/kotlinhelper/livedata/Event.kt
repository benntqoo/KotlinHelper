package com.jrtou.kotlinhelper.livedata

import androidx.lifecycle.Observer

open class Event<out T>(private val data: T) {
    //標記是否已被UI使用
    var hasBeenHandled = false
    fun getDataIfNotHandled(): T? = if (hasBeenHandled) null else {
        hasBeenHandled = true
        data
    }

    fun getData(): T = data
}

class ObserverEvent<T>(private val eventUnhandledData: (T) -> Unit) : Observer<Event<T>> {
    override fun onChanged(event: Event<T>?) {
        event?.getDataIfNotHandled()?.let { eventUnhandledData(it) }
    }
}