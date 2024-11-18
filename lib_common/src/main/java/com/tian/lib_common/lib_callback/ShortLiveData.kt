package com.tian.lib_common.lib_callback

import androidx.lifecycle.MutableLiveData

class ShortLiveData : MutableLiveData<Short>() {
    override fun getValue(): Short {
        return super.getValue() ?: 0
    }
}