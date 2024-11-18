package com.tian.lib_common.lib_event

/**
 * eventbus发送数据封装类
 */
open class EventMessage<V,E>(v: V, e: E) {
    var value = v
    var event = e
}