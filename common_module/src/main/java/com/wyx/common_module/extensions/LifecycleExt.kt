package com.wyx.common_module.extensions

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun <T> LifecycleOwner.observer(liveData: LiveData<T>, observer: Observer<T>) {
    liveData.observe(this, observer)
}