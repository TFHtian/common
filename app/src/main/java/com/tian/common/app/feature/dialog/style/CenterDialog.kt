package com.tian.common.app.feature.dialog.style

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.widget.AppCompatTextView
import com.lxj.xpopup.core.CenterPopupView
import com.tian.common.R
import com.tian.lib_common.lib_ext.clickNoRepeat

@SuppressLint("ViewConstructor")
class CenterDialog(
    context: Context,
    private val cancelCallback: (() -> Unit)? = null,
    private val sureCallback: ((result: String) -> Unit)? = null
): CenterPopupView(context) {

    override fun getImplLayoutId(): Int = R.layout.dialog_center

    override fun onCreate() {
        super.onCreate()
        findViewById<AppCompatTextView>(R.id.tv_cancel).clickNoRepeat {
            cancelCallback?.invoke()
            dismiss()
        }
        findViewById<AppCompatTextView>(R.id.tv_confirm).clickNoRepeat {
            sureCallback?.invoke("center")
            dismiss()
        }
    }

    override fun getMaxWidth(): Int = 0
}