package com.tian.common.app.feature.dialog.style

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.widget.AppCompatTextView
import com.lxj.xpopup.core.PositionPopupView
import com.lxj.xpopup.util.XPopupUtils
import com.tian.common.R
import com.tian.lib_common.lib_ext.clickNoRepeat

@SuppressLint("ViewConstructor")
class TopDialog(
    context: Context,
    private val callback: (() -> Unit)? = null
): PositionPopupView(context) {

    override fun getImplLayoutId() = R.layout.dialog_top

    override fun onCreate() {
        super.onCreate()
        findViewById<AppCompatTextView>(R.id.tv_confirm).clickNoRepeat {
            callback?.invoke()
            dismiss()
        }
    }

    override fun getPopupWidth() = XPopupUtils.getAppWidth(context)
}