package com.tian.common.app.feature.dialog.style

import android.annotation.SuppressLint
import android.content.Context
import com.lxj.xpopup.core.CenterPopupView
import com.tian.common.R
import com.tian.common.databinding.DialogPermissionApplyBinding
import com.tian.lib_common.lib_ext.clickNoRepeat

/**
 * 权限申请弹窗
 */
@SuppressLint("ViewConstructor")
class PermissionApplyDialog(
    context: Context,
    private val desc: String,
    private val cancelCallback: (() -> Unit)? = null,
    private val sureCallback: (() -> Unit)? = null
): CenterPopupView(context) {

    private lateinit var mBinding: DialogPermissionApplyBinding

    override fun getImplLayoutId(): Int = R.layout.dialog_permission_apply

    override fun onCreate() {
        super.onCreate()
        mBinding = DialogPermissionApplyBinding.bind(popupImplView)
        mBinding.tvContent.text = desc
        mBinding.run {
            tvCancel.clickNoRepeat {
                dismiss()
                cancelCallback?.invoke()
            }
            tvConfirm.clickNoRepeat {
                dismiss()
                sureCallback?.invoke()
            }
        }
    }
}