package com.tian.common.app.feature.keystore

import android.os.Bundle
import com.tian.common.R
import com.tian.common.app.util.KeyStoreHelper
import com.tian.common.databinding.ActivityKeyStoreBinding
import com.tian.lib_common.lib_base.mvvm.activity.BaseVmDbActivity
import com.tian.lib_common.lib_ext.clickNoRepeat
import com.tian.lib_common.lib_widget.titlebar.CommonTitleBar

class KeyStoreActivity: BaseVmDbActivity<KeyStoreViewModel, ActivityKeyStoreBinding>() {

    override fun onBindLayout(): Int = R.layout.activity_key_store

    override fun attachTitleBarView(): CommonTitleBar = mBinding.appbar.titleBar

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        setBarTitle(getString(R.string.key_store))
    }

    override fun initListener() {
        // 加密
        mBinding.tvEncode.clickNoRepeat {
            mBinding.tvEncodeCiphertext.text = KeyStoreHelper.encodeContent(mBinding.editContent.text.toString())
        }

        // 解密
        mBinding.tvDecode.clickNoRepeat {
            mBinding.tvDecodeInWriting.text = KeyStoreHelper.decodeContent(mBinding.tvEncodeCiphertext.text.toString())
        }
    }

    override fun createObserver() {}
}

