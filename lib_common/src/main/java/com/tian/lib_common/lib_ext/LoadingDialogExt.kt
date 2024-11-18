package com.tian.lib_common.lib_ext

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.core.BasePopupView
import com.tian.lib_common.lib_widget.loading.LoadingDialog

private var loadingDialog: BasePopupView? = null

/**
 * 打开等待框
 */
fun AppCompatActivity.showLoadingExt(message: String = "请求中…") {
    if (!this.isFinishing) {
        if (loadingDialog == null) {
            loadingDialog = XPopup.Builder(this)
                .hasShadowBg(false)
                .dismissOnTouchOutside(false)
                .customHostLifecycle(lifecycle)
                .asCustom(LoadingDialog(this,message))
        }
        loadingDialog?.show()
    }
}

/**
 * 打开等待框
 */
fun Fragment.showLoadingExt(message: String = "请求中…") {
    activity?.let {
        if (!it.isFinishing) {
            if (loadingDialog == null) {
                loadingDialog = XPopup.Builder(it)
                    .hasShadowBg(false)
                    .dismissOnTouchOutside(false)
                    .customHostLifecycle(lifecycle)
                    .asCustom(LoadingDialog(it,message))
            }
            loadingDialog?.show()
        }
    }
}

/**
 * 关闭等待框
 */
fun dismissActLoadingExt() {
    loadingDialog?.dismiss()
    loadingDialog = null
}

/**
 * 关闭等待框
 */
fun dismissFrgLoadingExt() {
    loadingDialog?.dismiss()
    loadingDialog = null
}