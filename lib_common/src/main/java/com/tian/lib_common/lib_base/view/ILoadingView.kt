package com.tian.lib_common.lib_base.view

interface ILoadingView {

    /**
     * 显示加载浮窗
     *
     * @param loadTip 加载文案
     */
    fun showLoading(loadTip: String = "请求中…")

    /**
     * 关闭加载浮窗
     */
    fun dismissLoading()
}