package com.tian.lib_common.lib_base.mvvm.fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tian.lib_common.lib_base.BaseFragment
import com.tian.lib_common.lib_base.mvvm.viewmodel.BaseViewModel
import com.tian.lib_common.lib_ext.getVMClass

abstract class BaseVmFragment<VM : BaseViewModel> : BaseFragment() {

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
        return obtainViewModel(getVMClass(this))
    }

    /**
     * 获取[ViewModel]
     */
    private fun <T : ViewModel> obtainViewModel(vmClass: Class<T>): T {
        return ViewModelProvider(
            viewModelStore,
            defaultViewModelProviderFactory,
            defaultViewModelCreationExtras
        )[vmClass]
    }

    /**
     * 创建LiveData数据观察者
     */
    abstract fun createObserver()

    /**
     * 注册UI 事件
     */
    protected open fun registerUIChange() {
        //初始加载
        mViewModel.mUIChangeLiveData.mRequestLoadingEvent.observe(this) {
            if (it.isShowLoading) {
                showLoading(it.loadTip)
            } else {
                dismissLoading()
            }
        }
        //请求加载
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
    }

}