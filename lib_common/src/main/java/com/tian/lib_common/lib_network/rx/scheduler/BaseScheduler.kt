package com.tian.lib_common.lib_network.rx.scheduler

import io.reactivex.rxjava3.core.*
import org.reactivestreams.Publisher

abstract class BaseScheduler<T : Any> protected constructor(
    private val subscribeOnScheduler: Scheduler,
    private val observeOnScheduler: Scheduler
) : ObservableTransformer<T, T>,
    SingleTransformer<T, T>,
    MaybeTransformer<T, T>,
    CompletableTransformer,
    FlowableTransformer<T, T> {

    override fun apply(upstream: Completable): CompletableSource {
        return upstream.subscribeOn(subscribeOnScheduler)
            .observeOn(observeOnScheduler)
    }

    override fun apply(upstream: Flowable<T>): Publisher<T> {
        return upstream.subscribeOn(subscribeOnScheduler)
            .observeOn(observeOnScheduler)
    }

    override fun apply(upstream: Maybe<T>): MaybeSource<T> {
        return upstream.subscribeOn(subscribeOnScheduler)
            .observeOn(observeOnScheduler)
    }

    override fun apply(upstream: Observable<T>): ObservableSource<T> {
        return upstream.subscribeOn(subscribeOnScheduler)
            .observeOn(observeOnScheduler)
    }

    override fun apply(upstream: Single<T>): SingleSource<T> {
        return upstream.subscribeOn(subscribeOnScheduler)
            .observeOn(observeOnScheduler)
    }
}
