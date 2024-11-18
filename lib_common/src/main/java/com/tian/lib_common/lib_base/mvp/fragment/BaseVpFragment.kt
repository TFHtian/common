package com.tian.lib_common.lib_base.mvp.fragment

import android.os.Bundle
import com.tian.lib_common.lib_base.BaseFragment
import com.tian.lib_common.lib_base.mvp.model.BaseModel
import com.tian.lib_common.lib_base.mvp.presenter.BasePresenter

abstract class BaseVpFragment<M : BaseModel, V, P : BasePresenter<M, V>> : BaseFragment() {

    protected var mPresenter: P? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        mPresenter = initPresenter()
        mPresenter?.attach(this as V)
        mPresenter?.injectLifecycle(mActivity)
        super.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        mPresenter?.detach()
        super.onDestroy()
    }

    abstract fun initPresenter(): P
}
