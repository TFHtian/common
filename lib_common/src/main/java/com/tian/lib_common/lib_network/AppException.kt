package com.tian.lib_common.lib_network

class AppException : Exception {

    var errorMsg: String //错误消息
    var errorCode: Int = 0 //错误码
    var errorLog: String? //错误日志
    var throwable: Throwable? = null

    constructor(errorCode: Int, errorMsg: String?, errorLog: String? = "", throwable: Throwable? = null) : super(errorMsg) {
        this.errorMsg = errorMsg ?: "请求失败，请稍后再试"
        this.errorCode = errorCode
        this.errorLog = errorLog ?: this.errorMsg
        this.throwable = throwable
    }

    constructor(error: Error, e: Throwable?) {
        errorCode = error.getKey()
        errorMsg = error.getValue()
        errorLog = e?.message
        throwable = e
    }
}