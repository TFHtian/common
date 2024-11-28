package com.tian.common.app.feature.dialog.style

import android.content.Context
import com.lxj.xpopup.core.BottomPopupView
import com.tian.common.R
import com.tian.common.databinding.DialogSoftTopBinding
import com.tian.lib_common.lib_ext.clickNoRepeat

class SoftTopDialog(context: Context): BottomPopupView(context) {

    private lateinit var mBinding: DialogSoftTopBinding

    override fun getImplLayoutId(): Int = R.layout.dialog_soft_top

    override fun onCreate() {
        super.onCreate()
        mBinding = DialogSoftTopBinding.bind(popupImplView)
        mBinding.tvCancel.clickNoRepeat {
            dismiss()
        }
    }
}