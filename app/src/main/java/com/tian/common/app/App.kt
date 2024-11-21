package com.tian.common.app

import android.content.Context
import androidx.multidex.MultiDex
import com.tencent.mmkv.MMKV
import com.tian.common.app.event.AppViewModel
import com.tian.common.app.event.EventViewModel
import com.tian.common.app.util.ActivityManager
import com.tian.common.app.util.KeyStoreHelper
import com.tian.lib_common.lib_base.BaseApp

/**
 * Application全局的ViewModel，里面存放了一些账户信息，基本配置信息等
 */
val appViewModel: AppViewModel by lazy { App.appViewModelInstance }

/**
 * Application全局的ViewModel，用于发送全局通知操作
 */
val eventViewModel: EventViewModel by lazy { App.eventViewModelInstance }

class App : BaseApp() {

    companion object {
        lateinit var instance: App
        lateinit var eventViewModelInstance: EventViewModel
        lateinit var appViewModelInstance: AppViewModel
    }

    override fun attachBaseContext(base: Context?) {
        instance = this
        MultiDex.install(this)
        super.attachBaseContext(base)
    }

    override fun onCreate() {
        super.onCreate()
        MMKV.initialize(this)
        KeyStoreHelper.init(this)
        registerActivityLifecycleCallbacks(ActivityManager.instance)
        eventViewModelInstance = getAppViewModelProvider()[EventViewModel::class.java]
        appViewModelInstance = getAppViewModelProvider()[AppViewModel::class.java]

    }
}