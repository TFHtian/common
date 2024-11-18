package com.tian.lib_common.lib_base.mvp.activity

import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.gyf.immersionbar.BarHide
import com.gyf.immersionbar.ImmersionBar
import com.tian.lib_common.R
import com.tian.lib_common.lib_base.mvp.model.BaseModel
import com.tian.lib_common.lib_base.mvp.presenter.BasePresenter
import com.tian.lib_common.lib_base.view.ITitleBarView
import com.tian.lib_common.lib_widget.titlebar.CommonTitleBarListener

abstract class BaseVpDbActivity<M : BaseModel, V, P : BasePresenter<M, V>, DB : ViewDataBinding> :
    BaseVpActivity<M, V, P>(), ITitleBarView {

    private var _binding: DB? = null
    protected val mBinding: DB get() = _binding!!

    override fun initContentView() {
        _binding = DataBindingUtil.setContentView(this, onBindLayout())
        _binding?.lifecycleOwner = this
        if (enableToolbar()) {
            initTitleBarView()
        }
    }

    override fun initImmersionBar() {
        if (attachTitleBarView() != null) {
            ImmersionBar.with(this)
                .statusBarColor(R.color.base_theme)
                .titleBar(attachTitleBarView())
                .statusBarDarkFont(false)
                .hideBar(BarHide.FLAG_HIDE_NAVIGATION_BAR)
                .init()
        } else {
            super.initImmersionBar()
        }
    }

    /**
     * 初始添加titleBar
     */
    private fun initTitleBarView() {
        setBarBgColor(getColor(R.color.base_theme))
        setBackIcon(R.drawable.ic_base_back)
        attachTitleBarView()?.run {
            setTitleBarListener(object : CommonTitleBarListener {
                override fun startBackClick() {
                    finishActivity()
                }

                override fun endTvClick() {
                    endTvClick()
                }

                override fun endIvClick() {
                    endIvClick()
                }
            })
        }
    }

    /**
     * 设置titleBar背景
     */
    override fun setBarBgColor(color: Int) {
        attachTitleBarView()?.run {
            setBarBgColor(color)
        }
    }

    /**
     * 设置返回图标
     */
    override fun setBackIcon(iconResId: Int) {
        attachTitleBarView()?.run {
            setBackIcon(iconResId)
        }
    }

    /**
     * 设置title文案
     */
    override fun setBarTitle(title: String) {
        attachTitleBarView()?.run {
            setBarTitle(title)
        }
    }

    /**
     * 设置title字体颜色
     */
    override fun setBarTitleColor(color: Int) {
        attachTitleBarView()?.run {
            setBarTitleColor(color)
        }
    }

    /**
     * 设置title字体大小
     */
    override fun setBarTitleSize(size: Float) {
        attachTitleBarView()?.run {
            setBarTitleSize(size)
        }
    }

    /**
     * 设置end文本
     */
    override fun setBarTvEnd(endText: String) {
        attachTitleBarView()?.run {
            setBarTvEnd(endText)
        }
    }

    /**
     * 设置title字体颜色
     */
    override fun setBarTvEndColor(color: Int) {
        attachTitleBarView()?.run {
            setBarTvEndColor(color)
        }
    }

    /**
     * 设置title字体大小
     */
    override fun setBarTvEndSize(size: Float) {
        attachTitleBarView()?.run {
            setBarTvEndSize(size)
        }
    }

    /**
     * 设置end图标
     */
    override fun setBarEndIcon(iconResId: Int) {
        attachTitleBarView()?.run {
            setBarEndIcon(iconResId)
        }
    }

    /**
     * 是否开启title栏
     */
    open fun enableToolbar(): Boolean {
        return true
    }

    /**
     * titleBar右边文本事件
     */
    open fun endTvClick(){}

    /**
     * titleBar右边图标事件
     */
    open fun endIvClick(){}
}