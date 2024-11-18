package com.tian.lib_common.lib_network.state

enum class LoadingType(var type: String, val desc: String) {
    INIT_LOADING("init_loading", "进入页面内初始loading"),
    REQUEST_LOADING("request_loading", "请求时带有蒙层loading")
}