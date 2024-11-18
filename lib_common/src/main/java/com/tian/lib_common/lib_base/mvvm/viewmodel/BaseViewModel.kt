package com.tian.lib_common.lib_base.mvvm.viewmodel

import androidx.lifecycle.ViewModel
import com.tian.lib_common.R
import com.tian.lib_common.lib_base.BaseStatusEntity
import com.tian.lib_common.lib_callback.EventLiveData
import com.tian.lib_common.lib_network.state.LoadingType

open class BaseViewModel : ViewModel() {

    val mStatusEntity: BaseStatusEntity by lazy { BaseStatusEntity() }
    val mUIChangeLiveData: UIChangeLiveData by lazy { UIChangeLiveData() }

    /**
     * 页面状态刷新通知
     */
    inner class UIChangeLiveData {
        // 请求加载弹窗
        val mRequestLoadingEvent by lazy { EventLiveData<BaseStatusEntity>() }
        // 请求加载
        val mInitLoadingEvent by lazy { EventLiveData<BaseStatusEntity>() }
        // 空数据状态
        val mNoDataViewEvent by lazy { EventLiveData<BaseStatusEntity>() }
        // 请求异常状态
        val mErrViewEvent by lazy { EventLiveData<BaseStatusEntity>() }
        // 成功状态
        val mSuccessEvent by lazy { EventLiveData<Boolean>() }
    }

    /**
     * ViewModel 层发布显示和关闭请求loading
     */
    open fun postRequestLoadingEvent(boolean: Boolean = false, loadTip: String = "请求中…") {
        mStatusEntity.isShowLoading = boolean
        mStatusEntity.loadTip = loadTip
        mUIChangeLiveData.mRequestLoadingEvent.value = mStatusEntity
    }

    /**
     * ViewModel 层发布初始页面loading
     */
    open fun postInitLoadingEvent(loadTip: String = "加载中…") {
        mStatusEntity.loadTip = loadTip
        mUIChangeLiveData.mInitLoadingEvent.value = mStatusEntity
    }

    /**
     * ViewModel 层发布开启loading：根据类型是否带有蒙层
     */
    open fun postShowLoadingEvent(loadingType: LoadingType, loadTip: String = "请求中…") {
        when(loadingType) {
            LoadingType.INIT_LOADING -> {
                postInitLoadingEvent(loadTip)
            }

            LoadingType.REQUEST_LOADING -> {
                postRequestLoadingEvent(true, loadTip)
            }
        }
    }

    /**
     * ViewModel 层发布关闭loading：根据类型是否带有蒙层
     */
    open fun postDismissLoadingEvent(loadingType: LoadingType) {
        when(loadingType) {
            LoadingType.INIT_LOADING -> {
                postSuccessViewEvent()
            }

            LoadingType.REQUEST_LOADING -> {
                postRequestLoadingEvent(false)
            }
        }
    }

    /**
     * ViewModel 层发布空数据状态
     */
    open fun postNoDataViewEvent(emptyTip: String = "暂无数据", emptyIconId: Int = R.drawable.ic_base_empty) {
        mStatusEntity.emptyTip = emptyTip
        mStatusEntity.emptyIconId = emptyIconId
        mUIChangeLiveData.mNoDataViewEvent.value = mStatusEntity
    }

    /**
     * ViewModel 层发布空数据状态
     */
    open fun postErrViewEvent(errorTip: String = "加载失败", errorRetry: String = "重试", errorIconId: Int = R.drawable.ic_base_error) {
        mStatusEntity.errorTip = errorTip
        mStatusEntity.errorRetry = errorRetry
        mStatusEntity.errorIconId = errorIconId
        mUIChangeLiveData.mErrViewEvent.value = mStatusEntity
    }

    /**
     * ViewModel 层发布成功页面状态
     */
    open fun postSuccessViewEvent(isSuccess: Boolean = true) {
        mUIChangeLiveData.mSuccessEvent.value = isSuccess
    }
}