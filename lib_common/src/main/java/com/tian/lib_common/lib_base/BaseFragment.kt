package com.tian.lib_common.lib_base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import com.tian.lib_common.lib_base.view.BaseView
import com.tian.lib_common.lib_event.EventMessage
import com.tian.lib_common.lib_ext.dismissFrgLoadingExt
import com.tian.lib_common.lib_ext.showLoadingExt
import com.tian.lib_common.lib_util.EventBusUtil
import com.tian.lib_common.lib_widget.pagestate.BaseEmptyState
import com.tian.lib_common.lib_widget.pagestate.BaseErrorState
import com.tian.lib_common.lib_widget.pagestate.BaseLoadingState
import com.trello.rxlifecycle4.components.support.RxAppCompatActivity
import com.zy.multistatepage.MultiStateContainer
import com.zy.multistatepage.bindMultiState
import com.zy.multistatepage.state.SuccessState
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

abstract class BaseFragment : Fragment(), BaseView {

    //父类activity
    lateinit var mActivity: RxAppCompatActivity
    // 绑定视图
    private var dataBindView: View? = null
    //是否第一次加载
    private var isFirst: Boolean = true
    //界面状态管理者
    private var uiMultiState : MultiStateContainer? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as RxAppCompatActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        isFirst = true
        dataBindView = initViewDataBind(inflater, container)
        val rootView = if (dataBindView == null) {
            inflater.inflate(onBindLayout(), container, false)
        } else {
            dataBindView
        }
        return if (attachStatePageView() == null) {
            initUiMultiState(rootView!!)
            uiMultiState
        } else {
            rootView
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (isRegisteredEventBus()) EventBusUtil.register(this)
        super.onViewCreated(view, savedInstanceState)
        initContentView()
        initPageStatusView()
        initParam()
        initView(savedInstanceState)
        initViewModel()
        initListener()
        initData()
    }

    override fun onResume() {
        super.onResume()
        onVisible()
    }

    /**
     * 是否需要懒加载
     */
    private fun onVisible() {
        if (lifecycle.currentState == Lifecycle.State.STARTED && isFirst) {
            view?.post {
                lazyLoadData()
                isFirst = false
            }
        }
    }

    /**
     * 初始页面状态布局
     */
    private fun initPageStatusView() {
        attachStatePageView()?.let {
            initUiMultiState(it)
        }
    }

    /**
     * 初始页面状态管理（加载loading、加载error、加载empty）
     */
    private fun initUiMultiState(view: View) {
        uiMultiState = view.bindMultiState()
    }

    /**
     * 子类绑定布局
     */
    open fun initViewDataBind(inflater: LayoutInflater, container: ViewGroup?): View? {
        return null
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
        dismissFrgLoadingExt()
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
        activity?.finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isRegisteredEventBus()) EventBusUtil.unregister(this)
    }

    /**
     * 默认不启用懒加载
     */
    open fun enableLazyData(): Boolean {
        return false
    }

    protected open fun initContentView() {}

    override fun initViewModel() {}

    override fun initParam() {}

    override fun initView(savedInstanceState: Bundle?) {}

    override fun initListener() {}

    override fun initData() {}

    protected open fun lazyLoadData() {}
}