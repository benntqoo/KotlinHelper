package com.jrtou.kotlinhelper.api

import androidx.lifecycle.LiveData

/**
 * A LiveData class that has `null` value.
 */
class LiveDataFactory<T : Any?> private constructor(data: T?) : LiveData<T>() {
    init {
        postValue(data)
    }

    companion object {
        fun <T> create(data: T?): LiveData<T> = LiveDataFactory(data)
    }
}