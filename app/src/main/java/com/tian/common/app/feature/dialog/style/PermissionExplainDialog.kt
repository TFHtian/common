package com.tian.common.app.feature.dialog.style

import android.annotation.SuppressLint
import android.content.Context
import com.lxj.xpopup.core.PositionPopupView
import com.lxj.xpopup.util.XPopupUtils
import com.tian.common.R
import com.tian.common.databinding.DialogPermissionExplainBinding

@SuppressLint("ViewConstructor")
class PermissionExplainDialog(
    context: Context,
    private val desc: String
): PositionPopupView(context) {

    private lateinit var mBinding: DialogPermissionExplainBinding

    override fun getImplLayoutId(): Int = R.layout.dialog_permission_explain

    override fun onCreate() {
        super.onCreate()
        mBinding = DialogPermissionExplainBinding.bind(popupImplView)
        mBinding.tvDesc.text = desc
    }

    override fun getPopupWidth(): Int {
        return XPopupUtils.getAppWidth(context)
    }
}