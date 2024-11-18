package com.tian.lib_common.lib_base.mvvm.activity

import androidx.lifecycle.ViewModelProvider
import com.tian.lib_common.lib_base.BaseActivity
import com.tian.lib_common.lib_base.mvvm.viewmodel.BaseViewModel
import com.tian.lib_common.lib_ext.getVmClazz

abstract class BaseVmActivity<VM : BaseViewModel> : BaseActivity() {

    lateinit var mViewModel: VM

    override fun initViewModel() {
        mViewModel = createViewModel()
        registerUIChange()
        createObserver()
    }

    /**
     * 创建viewModel
     */
    private fun createViewModel(): VM {
        return ViewModelProvider(this)[getVmClazz(this)]
    }

    /**
     * 创建LiveData数据观察者
     */
    abstract fun createObserver()

    /**
     * 注册UI 事件
     */
    protected open fun registerUIChange() {
        //  请求加载
        mViewModel.mUIChangeLiveData.mRequestLoadingEvent.observe(this) {
            if (it.isShowLoading) {
                showLoading(it.loadTip)
            } else {
                dismissLoading()
            }
        }
        // 初始加载
        mViewModel.mUIChangeLiveData.mInitLoadingEvent.observe(this) {
            showLoadingUI(it.loadTip)
        }
        // 空数据状态
        mViewModel.mUIChangeLiveData.mNoDataViewEvent.observe(this) {
            showEmptyUI(it.emptyTip, it.emptyIconId)
        }
        // 请求异常状态
        mViewModel.mUIChangeLiveData.mErrViewEvent.observe(this) {
            showErrorUI(it.errorTip, it.errorRetry, it.errorIconId)
        }
        // 成功状态
        mViewModel.mUIChangeLiveData.mSuccessEvent.observe(this) {
            showSuccessUI()
        }
    }
}