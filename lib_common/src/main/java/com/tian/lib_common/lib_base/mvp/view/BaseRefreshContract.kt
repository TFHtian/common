package com.tian.lib_common.lib_base.mvp.view

import com.tian.lib_common.lib_base.view.BaseRefreshView
import com.tian.lib_common.lib_base.view.BaseView

interface BaseRefreshContract {

    interface View : BaseView, BaseRefreshView
}