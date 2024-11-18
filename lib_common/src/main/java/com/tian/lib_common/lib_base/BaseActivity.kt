package com.tian.lib_common.lib_base

import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import com.gyf.immersionbar.BarHide
import com.gyf.immersionbar.ImmersionBar
import com.tian.lib_common.R
import com.tian.lib_common.lib_base.view.BaseView
import com.tian.lib_common.lib_event.EventMessage
import com.tian.lib_common.lib_ext.dismissActLoadingExt
import com.tian.lib_common.lib_ext.showLoadingExt
import com.tian.lib_common.lib_util.EventBusUtil
import com.tian.lib_common.lib_widget.pagestate.BaseEmptyState
import com.tian.lib_common.lib_widget.pagestate.BaseErrorState
import com.tian.lib_common.lib_widget.pagestate.BaseLoadingState
import com.trello.rxlifecycle4.components.support.RxAppCompatActivity
import com.zy.multistatepage.MultiStateContainer
import com.zy.multistatepage.bindMultiState
import com.zy.multistatepage.state.SuccessState
import me.jessyan.autosize.AutoSizeCompat
import me.jessyan.autosize.internal.CustomAdapt
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

abstract class BaseActivity : RxAppCompatActivity(), BaseView, CustomAdapt {

    //界面状态管理者
    private var uiMultiState : MultiStateContainer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        if (isRegisteredEventBus()) EventBusUtil.register(this)
        super.onCreate(savedInstanceState)
        initContentView()
        initPageStatusView()
        initImmersionBar()
        initViewModel()
        initParam()
        initView(savedInstanceState)
        initListener()
        initData()
    }

    /**
     * 初始视图
     */
    protected open fun initContentView() {
        setContentView(onBindLayout())
    }

    /**
     * 初始页面状态管理（加载loading、加载error、加载empty）
     */
    private fun initPageStatusView() {
        uiMultiState = if (attachStatePageView() != null) {
            attachStatePageView()!!.bindMultiState()
        } else {
            bindMultiState()
        }
    }

    /**
     * 初始状态栏
     */
    protected open fun initImmersionBar() {
        ImmersionBar.with(this)
            .statusBarColor(R.color.base_theme)
            .statusBarDarkFont(false)
            .hideBar(BarHide.FLAG_HIDE_NAVIGATION_BAR)
            .init()
    }

    /**
     * 注册视图
     */
    abstract fun onBindLayout(): Int

    /**
     * 显示加载浮窗
     *
     * @param loadTip 加载文案
     */
    override fun showLoading(loadTip: String) {
        showLoadingExt(loadTip)
    }

    /**
     * 关闭加载浮窗
     */
    override fun dismissLoading() {
        dismissActLoadingExt()
    }

    /**
     * 展示加载中界面
     *
     * @param loadTip 加载文案
     */
    override fun showLoadingUI(loadTip: String) {
        uiMultiState?.show<BaseLoadingState> {
            it.setLoadingTip(loadTip)
        }
    }

    /**
     * 展示无数据界面
     *
     * @param emptyTip    无数据提示文案
     * @param emptyIconId 无数据提示图标
     */
    override fun showEmptyUI(emptyTip: String, emptyIconId: Int) {
        uiMultiState?.show<BaseEmptyState> {
            it.setEmptyTip(emptyTip)
            it.setEmptyIcon(emptyIconId)
        }
    }

    /**
     * 展示加载异常界面
     *
     * @param errorTip    异常提示文案
     * @param errorRetry  异常重试文案
     * @param errorIconId 异常提示图标
     */
    override fun showErrorUI(errorTip: String, errorRetry: String, errorIconId: Int) {
        uiMultiState?.show<BaseErrorState> {
            it.setErrorTip(errorTip)
            it.setErrorRetry(errorRetry)
            it.setErrorIcon(errorIconId)
            it.retry {
                onLoadRetry()
            }
        }
    }

    /**
     * 空界面，错误界面 点击重试时触发的方法
     */
    override fun onLoadRetry() {}

    /**
     * 显示 成功状态界面
     */
    override fun showSuccessUI() {
        uiMultiState?.show<SuccessState>()
    }

    /**
     * 注册eventbus
     */
    open fun isRegisteredEventBus(): Boolean {
        return true
    }

    /**
     * 接收到分发的事件
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    open fun onReceiveEvent(event: EventMessage<*, *>?) {}

    /**
     * 接受到分发的粘性事件
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    open fun onReceiveStickyEvent(event: EventMessage<*, *>?) {}

    /**
     * 按照宽度适配
     */
    override fun isBaseOnWidth(): Boolean = if (isWidth() == null) {
        resources.configuration?.orientation == Configuration.ORIENTATION_PORTRAIT
    } else {
        isWidth() ?: true
    }

    /**
     * 设计图上的设计尺寸, 单位 dp
     */
    override fun getSizeInDp(): Float = if (isWidth() == null) {
        screenDesignWidth()
    } else {
        if (isWidth() == true) screenDesignWidth() else screenDesignHeight()
    }

    /**
     * true 使用宽度适配（默认）
     *
     * false 使用高度适配
     */
    open fun isWidth(): Boolean? = true

    override fun getResources(): Resources {
        AutoSizeCompat.autoConvertDensity(
            (super.getResources()),
            screenDesignWidth(),
            if (isWidth() == null) {
                super.getResources().configuration?.orientation == Configuration.ORIENTATION_PORTRAIT
            } else {
                isWidth() ?: true
            }
        )
        return super.getResources()
    }

    /**
     * 设置屏幕适配宽 设计图的宽度.
     *
     * 375（默认）
     */
    open fun screenDesignWidth(): Float = 375F

    /**
     * 设置屏幕适配的高 设计图的宽度.
     *
     * 640（默认）
     */
    open fun screenDesignHeight(): Float = 640F

    /**
     * 内存不足 手动调用GC
     */
    override fun onLowMemory() {
        System.gc()
        super.onLowMemory()
    }

    /**
     * 关闭页面
     */
    override fun finishActivity() {
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isRegisteredEventBus()) EventBusUtil.unregister(this)
    }

    override fun initViewModel() {}

    override fun initParam() {}

    override fun initView(savedInstanceState: Bundle?) {}

    override fun initListener() {}

    override fun initData() {}
}