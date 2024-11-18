package com.tian.lib_common.lib_callback

import androidx.lifecycle.MutableLiveData

class ByteLiveData : MutableLiveData<Byte>() {
    override fun getValue(): Byte {
        return super.getValue() ?: 0
    }
}