package com.tian.lib_common.lib_base.view

import com.tian.lib_common.lib_widget.titlebar.CommonTitleBar

interface ITitleBarView {

    /**
     * 注入title栏
     * 子类需要绑定titleBarView
     */
    fun attachTitleBarView(): CommonTitleBar? {
        return null
    }

    /**
     * 设置titleBar背景
     */
    fun setBarBgColor(color: Int)

    /**
     * 设置返回图标
     */
    fun setBackIcon(iconResId: Int)

    /**
     * 设置title文案
     */
    fun setBarTitle(title: String)

    /**
     * 设置title字体颜色
     */
    fun setBarTitleColor(color: Int)

    /**
     * 设置title字体大小
     */
    fun setBarTitleSize(size: Float)


    /**
     * 设置end文本
     */
    fun setBarTvEnd(endText: String)

    /**
     * 设置title字体颜色
     */
    fun setBarTvEndColor(color: Int)

    /**
     * 设置title字体大小
     */
    fun setBarTvEndSize(size: Float)

    /**
     * 设置end图标
     */
    fun setBarEndIcon(iconResId: Int)
}