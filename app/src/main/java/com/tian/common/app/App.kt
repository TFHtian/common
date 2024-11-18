package com.tian.common.app

import androidx.multidex.MultiDex
import com.tencent.mmkv.MMKV
import com.tian.common.app.event.AppViewModel
import com.tian.common.app.event.EventViewModel
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

    override fun onCreate() {
        super.onCreate()
        MMKV.initialize(this)
        instance = this
        eventViewModelInstance = getAppViewModelProvider().get(EventViewModel::class.java)
        appViewModelInstance = getAppViewModelProvider().get(AppViewModel::class.java)
        MultiDex.install(this)
    }
}