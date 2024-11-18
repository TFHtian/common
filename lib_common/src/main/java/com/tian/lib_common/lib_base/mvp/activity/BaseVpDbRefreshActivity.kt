package com.tian.lib_common.lib_base.mvp.activity

import androidx.databinding.ViewDataBinding
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.tian.lib_common.lib_base.mvp.model.BaseModel
import com.tian.lib_common.lib_base.mvp.presenter.BaseRefreshPresenter
import com.tian.lib_common.lib_base.mvp.view.BaseRefreshContract

abstract class BaseVpDbRefreshActivity<M : BaseModel, V : BaseRefreshContract.View, P : BaseRefreshPresenter<M, V>, DB : ViewDataBinding> :
    BaseVpDbActivity<M, V, P, DB>(), BaseRefreshContract.View{

    private var mRefreshLayout: SmartRefreshLayout? = null

    override fun initContentView() {
        super.initContentView()
        initRefreshView()
    }

    /**
     * 初始化刷新组件
     */
    private fun initRefreshView() {
        // 绑定组件
        mRefreshLayout = attachRefreshLayout()
        // 是否开启刷新
        enableRefresh(enableRefresh())
        // 是否开启加载更多
        enableLoadMore(enableLoadMore())
        // 下拉刷新
        mRefreshLayout?.setOnRefreshListener {
            onRefreshEvent()
        }
        // 上拉加载
        mRefreshLayout?.setOnLoadMoreListener {
            onLoadMoreEvent()
        }
    }

    override fun enableRefresh(b: Boolean) {
        mRefreshLayout?.setEnableRefresh(b)
    }

    override fun enableLoadMore(b: Boolean) {
        mRefreshLayout?.setEnableLoadMore(b)
    }

    override fun enableAutoLoadMore(b: Boolean) {
        mRefreshLayout?.setEnableAutoLoadMore(b)
    }

    override fun onAutoLoadEvent() {}

    override fun autoLoadData() {
        mRefreshLayout?.autoRefresh()
    }

    override fun stopRefresh(boolean: Boolean) {
        mRefreshLayout?.finishRefresh(boolean)
    }

    override fun stopLoadMore(boolean: Boolean) {
        mRefreshLayout?.finishLoadMore(boolean)
    }

    override fun stopLoadMoreWithNoMoreData() {
        mRefreshLayout?.finishLoadMoreWithNoMoreData()
    }

    abstract fun attachRefreshLayout(): SmartRefreshLayout?

    open fun enableRefresh(): Boolean {
        return true
    }

    open fun enableLoadMore(): Boolean {
        return true
    }
}