package com.tian.lib_common.lib_network.rx

import com.tian.lib_common.lib_network.rx.scheduler.IoMainScheduler

object SchedulerUtils {

    fun <T : Any> ioToMain(): IoMainScheduler<T> = IoMainScheduler()
}