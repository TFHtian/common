package com.tian.common.app.feature.imageloader

import android.os.Bundle
import com.tian.common.R
import com.tian.common.databinding.ActivityImageLoaderBinding
import com.tian.lib_common.lib_base.mvvm.activity.BaseVmDbActivity
import com.tian.lib_common.lib_base.mvvm.viewmodel.BaseViewModel
import com.tian.lib_common.lib_widget.titlebar.CommonTitleBar

class ImageLoaderActivity: BaseVmDbActivity<BaseViewModel, ActivityImageLoaderBinding>() {

    override fun onBindLayout(): Int = R.layout.activity_image_loader

    override fun attachTitleBarView(): CommonTitleBar = mBinding.appbar.titleBar

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        setBarTitle(getString(R.string.image_loader))
    }

    override fun createObserver() {}
}