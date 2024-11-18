package com.tian.lib_common.lib_callback

import androidx.lifecycle.MutableLiveData

class FloatLiveData(value: Float = 0f) : MutableLiveData<Float>(value) {
    override fun getValue(): Float {
        return super.getValue()!!
    }
}