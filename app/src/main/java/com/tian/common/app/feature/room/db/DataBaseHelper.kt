package com.tian.common.app.feature.room.db

import android.annotation.SuppressLint
import android.util.Log
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.functions.Action
import io.reactivex.rxjava3.functions.Consumer
import io.reactivex.rxjava3.schedulers.Schedulers

class DataBaseHelper private constructor() {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    companion object {
        // Kotlin 优化的单例实现方式
        val instance: DataBaseHelper by lazy { DataBaseHelper() }
    }

    // 添加 Single 类型的 Disposable
    @SuppressLint("CheckResult")
    fun <T : Any> addDisposable(single: Single<T>, consumer: Consumer<T>) {
        single.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(consumer, this::handleError)
    }

    // 添加 Flowable 类型的 Disposable
    fun <T : Any> addDisposable(flowable: Flowable<T>, consumer: Consumer<T>) {
        compositeDisposable.add(
            flowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(consumer, this::handleError)
        )
    }

    // 添加 Completable 类型的 Disposable
    fun addDisposable(completable: Completable, action: Action) {
        compositeDisposable.add(
            completable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(action, this::handleError)
        )
    }

    // 错误处理方法
    private fun handleError(throwable: Throwable) {
        // 可以记录日志或进行适当的错误处理
        Log.e("DataBaseHelper", "Error occurred", throwable)
    }

    // 清理所有的 Disposable
    fun clearDisposables() {
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.clear()
        }
    }
}