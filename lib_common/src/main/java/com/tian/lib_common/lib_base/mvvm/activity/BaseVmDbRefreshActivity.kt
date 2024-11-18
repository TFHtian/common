package com.tian.lib_common.lib_base.mvvm.activity

import androidx.databinding.ViewDataBinding
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.tian.lib_common.lib_base.mvvm.viewmodel.BaseRefreshViewModel
import com.tian.lib_common.lib_base.view.BaseRefreshView

abstract class BaseVmDbRefreshActivity<VM : BaseRefreshViewModel, DB : ViewDataBinding> :
    BaseVmDbActivity<VM, DB>(), BaseRefreshView {

    private var mRefreshLayout: SmartRefreshLayout? = null

    override fun initContentView() {
        super.initContentView()
        initRefreshView()
    }

    override fun registerUIChange() {
        super.registerUIChange()
        registerUIRefreshChange()
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

    /**
     * 初始化观察者 ViewModel 层加载完数据的回调通知当前页面事件已完成
     */
    private fun registerUIRefreshChange() {
        mViewModel.mUIRefreshChangeLiveData.mAutoRefreshLiveEvent.observe(this) {
            autoLoadData()
        }
        mViewModel.mUIRefreshChangeLiveData.mStopRefreshLiveEvent.observe(this) {
            stopRefresh(it)
        }
        mViewModel.mUIRefreshChangeLiveData.mStopLoadMoreLiveEvent.observe(this) {
            stopLoadMore(it)
        }
        mViewModel.mUIRefreshChangeLiveData.mStopNoMoreDataLiveEvent.observe(this) {
            stopLoadMoreWithNoMoreData()
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