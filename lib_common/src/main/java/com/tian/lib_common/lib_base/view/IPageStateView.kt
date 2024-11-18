package com.tian.lib_common.lib_base.view

import android.view.View
import com.tian.lib_common.R

/**
 * 页面状态管理
 */
interface IPageStateView {

    /**
     * 绑定页面状态View
     * 子类可传入需要被包裹的View，做状态显示-空、错误、加载
     */
    fun attachStatePageView(): View? {
        return null
    }

    /**
     * 展示加载中界面
     *
     * @param loadTip 加载文案
     */
    fun showLoadingUI(loadTip: String = "加载中…")

    /**
     * 展示无数据界面
     *
     * @param emptyTip    无数据提示文案
     * @param emptyIconId 无数据提示图标
     */
    fun showEmptyUI(emptyTip: String = "暂无数据",
                    emptyIconId: Int = R.drawable.ic_base_empty)

    /**
     * 展示加载异常界面
     *
     * @param errorTip    异常提示文案
     * @param errorRetry  异常重试文案
     * @param errorIconId 异常提示图标
     */
    fun showErrorUI(errorTip: String = "加载失败",
                    errorRetry: String = "重试",
                    errorIconId: Int = R.drawable.ic_base_error)

    /**
     * 当界面是错误界面，空界面时，点击触发重试
     */
    fun onLoadRetry()

    /**
     * 界面显示加载成功
     */
    fun showSuccessUI()
}