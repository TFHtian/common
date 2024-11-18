package com.tian.lib_common.lib_base.mvp.model

import android.content.Context
import com.trello.rxlifecycle4.LifecycleProvider

open class BaseModel(val context: Context) {

    var lifecycle: LifecycleProvider<*>? = null
        private set

    fun injectLifecycle(lifecycle: LifecycleProvider<*>) {
        this.lifecycle = lifecycle
    }

    fun destroy() {}
}
