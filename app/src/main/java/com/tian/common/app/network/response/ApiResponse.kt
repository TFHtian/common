package com.tian.common.app.network.response

import com.tian.lib_common.lib_network.BaseResponse

/**
 * 1.继承 BaseResponse
 * 2.重写isSuccess 方法，编写你的业务需求，根据自己的条件判断数据是否请求成功
 * 3.重写 getResponseCode、getResponseData、getResponseMsg方法，传入你的 code data msg
 */
data class ApiResponse<T>(val errorCode: Int, val errorMsg: String, val data: T) : BaseResponse<T>() {

    // 这里是示例 0 就代表请求成功，根据业务需求来改变
    override fun isSuccess() = errorCode == 0

    override fun getResponseCode() = errorCode

    override fun getResponseData() = data

    override fun getResponseMsg() = errorMsg
}