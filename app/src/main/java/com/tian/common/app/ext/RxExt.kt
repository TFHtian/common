package com.tian.common.app.ext

import com.tian.common.app.network.response.ApiResponse
import com.tian.lib_common.lib_base.view.BaseView
import com.tian.lib_common.lib_ext.loge
import com.tian.lib_common.lib_network.AppException
import com.tian.lib_common.lib_network.rx.ResultObserver
import com.tian.lib_common.lib_network.rx.SchedulerUtils
import com.tian.lib_common.lib_network.state.LoadingType
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

/**
 * 绑定mvp封装调用
 */
fun <T> Observable<ApiResponse<T>>.executeResult(
    onSuccess: (T?) -> Unit,
    disposable: (Disposable) -> Unit = {},
    onError: (AppException) -> Unit = {},
    view: BaseView? = null,
    isShowDialog: Boolean = false,
    loadingMessage: String = "请求网络中...",
    loadingType: LoadingType = LoadingType.REQUEST_LOADING
) {
    return this.compose(SchedulerUtils.ioToMain())
        .subscribe(object : ResultObserver<T>() {

            override fun onSubscribe(d: Disposable) {
                super.onSubscribe(d)
                disposable(d)
            }

            override fun onRequestStart() {
                view?.let {
                    when(loadingType) {
                        LoadingType.INIT_LOADING -> {
                            if (isShowDialog) it.showLoadingUI(loadingMessage)
                        }
                        LoadingType.REQUEST_LOADING -> {
                            if (isShowDialog) it.showLoading(loadingMessage)
                        }
                    }
                }
            }

            override fun onSuccess(result: T?) {
                onSuccess.invoke(result)
            }

            override fun onFailure(error: AppException) {
                //打印错误消息
                error.message?.loge()
                //打印错误栈信息
                error.throwable?.printStackTrace()
                //失败回调
                onError.invoke(error)
            }

            override fun onRequestEnd() {
                view?.let {
                    when(loadingType) {
                        LoadingType.INIT_LOADING -> {
                            if (isShowDialog) it.showSuccessUI()
                        }
                        LoadingType.REQUEST_LOADING -> {
                            if (isShowDialog) it.dismissLoading()
                        }
                    }
                }
            }
        })
}

/**
 * 普通调用
 */
fun <T> executeResult(observable: Observable<ApiResponse<T>>?, subscriber: ResultObserver<T>) {
    observable?.subscribeOn(Schedulers.io())?.observeOn(AndroidSchedulers.mainThread())?.subscribe(subscriber)
}