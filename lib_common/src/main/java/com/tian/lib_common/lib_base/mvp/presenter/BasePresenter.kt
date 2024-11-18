package com.tian.lib_common.lib_base.mvp.presenter

import android.content.Context
import com.trello.rxlifecycle4.LifecycleProvider
import com.tian.lib_common.lib_base.mvp.model.BaseModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

abstract class BasePresenter<M : BaseModel, V>(protected var mContext: Context) {

    protected var mView: V? = null
    protected var mModel: M? = null
    private var mCompositeDisposable: CompositeDisposable? = null

    fun attach(view: V) {
        attachView(view)
        attachModel()
    }

    fun detach() {
        unDispose()
        detachView()
        detachModel()
        this.mCompositeDisposable = null
    }

    private fun attachView(view: V) {
        mView = view
    }

    private fun detachView() {
        mView = null
    }

    private fun attachModel() {
        mModel = initModel()
    }

    private fun detachModel() {
        mModel?.destroy()
        mModel = null
    }

    open fun addDisposable(disposable: Disposable?) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = CompositeDisposable()
        }
        disposable?.let { mCompositeDisposable?.add(it) }
    }

    private fun unDispose() {
        mCompositeDisposable?.clear()
        mCompositeDisposable = null
    }

    abstract fun initModel(): M

    fun injectLifecycle(lifecycle: LifecycleProvider<*>) {
        mModel?.injectLifecycle(lifecycle)
    }
}
