package com.tomaszkopacz.kawernaapp.util.extensions

import androidx.lifecycle.LiveData
import com.tomaszkopacz.kawernaapp.util.test.OneTimeObserver

fun <T> LiveData<T>.observeOnce(onChangeHandler: (T) -> Unit) {
    val observer = OneTimeObserver(onChangeHandler)
    observe(observer, observer)
}