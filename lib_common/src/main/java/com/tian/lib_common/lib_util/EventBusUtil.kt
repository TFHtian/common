package com.tian.lib_common.lib_util

import com.tian.lib_common.lib_event.EventMessage
import org.greenrobot.eventbus.EventBus

/**
 * Eventbus事件封装
 */
object EventBusUtil {

    /**
     * 注册 EventBus
     */
    fun register(subscriber: Any?) {
        val eventBus: EventBus = EventBus.getDefault()
        if (!eventBus.isRegistered(subscriber)) {
            eventBus.register(subscriber)
        }
    }

    /**
     * 解除注册 EventBus
     */
    fun unregister(subscriber: Any?) {
        val eventBus: EventBus = EventBus.getDefault()
        if (eventBus.isRegistered(subscriber)) {
            eventBus.unregister(subscriber)
        }
    }

    /**
     * 发送事件消息
     */
    fun post(event: EventMessage<*, *>?) {
        EventBus.getDefault().post(event)
    }

    /**
     * 发送粘性事件消息
     */
    fun postSticky(event: EventMessage<*, *>?) {
        EventBus.getDefault().postSticky(event)
    }
}