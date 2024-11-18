package com.tian.lib_common.lib_network.rx

import com.tian.lib_common.lib_network.AppException
import com.tian.lib_common.lib_network.BaseResponse
import com.tian.lib_common.lib_network.ExceptionHandle
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable

abstract class ResultObserver<T> : Observer<BaseResponse<T>> {

    override fun onSubscribe(d: Disposable) {
        if (!d.isDisposed) {
            onRequestStart()
        }
    }

    override fun onNext(response: BaseResponse<T>) {
        onRequestEnd()
        if (response.isSuccess()) {
            try {
                onSuccess(response.getResponseData())
            } catch (e: Exception) {
                e.printStackTrace()
            }

        } else {
            try {
                onFailure(AppException(
                    response.getResponseCode(),
                    response.getResponseMsg(),
                    response.getResponseMsg()
                ))
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }

    override fun onError(e: Throwable) {
        onRequestEnd()
        try {
            onFailure(ExceptionHandle.handleException(e))
        } catch (e1: Exception) {
            e1.printStackTrace()
        }
    }

    override fun onComplete() {}

    open fun onRequestStart() {}

    open fun onRequestEnd() {}

    /**
     * 返回成功
     *
     * @param result
     * @throws Exception
     */
    @Throws(Exception::class)
    abstract fun onSuccess(result: T?)

    /**
     * 返回失败
     *
     * @throws Exception
     */
    @Throws(Exception::class)
    abstract fun onFailure(error: AppException)
}
