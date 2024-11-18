package com.tian.lib_common.lib_base.mvvm.viewmodel

import com.tian.lib_common.lib_callback.EventLiveData

abstract class BaseRefreshViewModel: BaseViewModel() {

    val mUIRefreshChangeLiveData: UIRefreshChangeLiveData by lazy { UIRefreshChangeLiveData() }

    /**
     * 页面列表刷新通知
     */
    inner class UIRefreshChangeLiveData {
        // 自动刷新
        val mAutoRefreshLiveEvent by lazy { EventLiveData<Void>() }
        // 停止刷新
        val mStopRefreshLiveEvent by lazy { EventLiveData<Boolean>() }
        // 停止加载更多
        val mStopLoadMoreLiveEvent by lazy { EventLiveData<Boolean>() }
        // 停止加载且没有更多数据
        val mStopNoMoreDataLiveEvent by lazy { EventLiveData<Void>() }
    }

    /**
     * ViewModel 层发布自动刷新事件
     */
    open fun postAutoRefreshEvent() {
        mUIRefreshChangeLiveData.mAutoRefreshLiveEvent.call()
    }

    /**
     * ViewModel 层发布停止刷新事件
     * @param boolean false 刷新失败
     */
    open fun postStopRefreshEvent(boolean: Boolean = true) {
        mUIRefreshChangeLiveData.mStopRefreshLiveEvent.value = boolean
    }

    /**
     * ViewModel 层发布停止加载更多
     * @param boolean false 加载失败
     */
    open fun postStopLoadMoreEvent(boolean: Boolean = true) {
        mUIRefreshChangeLiveData.mStopLoadMoreLiveEvent.value = boolean
    }

    /**
     * ViewModel 层发布完成加载并标记没有更多数据
     */
    open fun postStopNoMoreDataLiveEvent() {
        mUIRefreshChangeLiveData.mStopNoMoreDataLiveEvent.call()
    }
}