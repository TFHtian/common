package com.tian.lib_common.lib_base.mvvm.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.tian.lib_common.lib_base.mvvm.viewmodel.BaseViewModel

abstract class BaseVmDbFragment<VM : BaseViewModel, DB : ViewDataBinding> : BaseVmFragment<VM>(){

    private var _binding: DB? = null
    protected val mBinding: DB get() = _binding!!

    override fun initViewDataBind(inflater: LayoutInflater, container: ViewGroup?): View? {
        _binding = DataBindingUtil.inflate(inflater, onBindLayout(), container, false)
        _binding?.lifecycleOwner = viewLifecycleOwner
        return mBinding.root
    }
}