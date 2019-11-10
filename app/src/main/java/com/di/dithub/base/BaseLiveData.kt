package com.di.dithub.base

import android.os.Looper
import androidx.lifecycle.LiveData

open class BaseLiveData<T>(initValue: T) : LiveData<T>() {

    init {
        super.setValue(initValue)
    }

    open fun update(value: T) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            super.setValue(value)
        } else {
            postValue(value)
        }
    }
}