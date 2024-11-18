package com.tian.lib_common.lib_base.mvp.activity

import android.os.Bundle
import com.tian.lib_common.lib_base.BaseActivity
import com.tian.lib_common.lib_base.mvp.model.BaseModel
import com.tian.lib_common.lib_base.mvp.presenter.BasePresenter

abstract class BaseVpActivity<M : BaseModel, V, P : BasePresenter<M, V>> : BaseActivity() {

    protected var mPresenter: P? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        mPresenter = initPresenter()
        mPresenter?.attach(this as V)
        mPresenter?.injectLifecycle(this)
        super.onCreate(savedInstanceState)
    }

    abstract fun initPresenter(): P

    override fun onDestroy() {
        super.onDestroy()
        mPresenter?.detach()
    }
}