package com.tian.lib_common.lib_base.mvp.presenter

import android.content.Context
import com.tian.lib_common.lib_base.mvp.model.BaseModel
import com.tian.lib_common.lib_base.mvp.view.BaseRefreshContract

abstract class BaseRefreshPresenter<M : BaseModel, V : BaseRefreshContract.View>(context: Context) : BasePresenter<M, V>(context)