package com.tian.common.app.feature.dialog.style

import android.content.Context
import com.lxj.xpopup.core.AttachPopupView
import com.tian.common.R
import com.tian.common.databinding.DialogVerticalBinding

class VerticalDialog(context: Context): AttachPopupView(context) {

    private lateinit var mBinding: DialogVerticalBinding

    override fun getImplLayoutId(): Int = R.layout.dialog_vertical

    override fun onCreate() {
        super.onCreate()
        mBinding = DialogVerticalBinding.bind(popupImplView)
    }
}