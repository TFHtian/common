package com.tian.lib_common.lib_callback

import androidx.lifecycle.MutableLiveData

class DoubleLiveData : MutableLiveData<Double>() {
    override fun getValue(): Double {
        return super.getValue() ?: 0.0
    }
}