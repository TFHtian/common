package com.tian.lib_common.lib_base.mvp.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.tian.lib_common.lib_base.mvp.model.BaseModel
import com.tian.lib_common.lib_base.mvp.presenter.BasePresenter

abstract class BaseVpDbFragment<M : BaseModel, V, P : BasePresenter<M, V>, DB : ViewDataBinding> :
    BaseVpFragment<M, V, P>(){

    private var _binding: DB? = null
    protected val mBinding: DB get() = _binding!!

    override fun initViewDataBind(inflater: LayoutInflater, container: ViewGroup?): View? {
        _binding = DataBindingUtil.inflate(inflater, onBindLayout(), container, false)
        _binding?.lifecycleOwner = viewLifecycleOwner
        return mBinding.root
    }
}