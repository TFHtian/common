package com.tian.lib_common.lib_base.view

import android.os.Bundle

interface BaseView : ILoadingView, IPageStateView {

    /**
     * 初始化viewModel
     */
    fun initViewModel()

    /**
     * 初始化界面传递参数
     */
    fun initParam()

    /**
     * 初始化页面view
     */
    fun initView(savedInstanceState: Bundle?)

    /**
     * 初始化页面事件监听
     */
    fun initListener()

    /**
     * 初始化数据
     */
    fun initData()

    /**
     * 结束页面
     */
    fun finishActivity()
}