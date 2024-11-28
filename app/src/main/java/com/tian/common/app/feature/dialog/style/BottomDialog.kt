package com.tian.common.app.feature.dialog.style

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.widget.AppCompatTextView
import com.lxj.xpopup.core.BottomPopupView
import com.tian.common.R
import com.tian.lib_common.lib_ext.clickNoRepeat

@SuppressLint("ViewConstructor")
class BottomDialog(
    context: Context,
    private val callback: (() -> Unit)? = null
): BottomPopupView(context) {

    override fun getImplLayoutId(): Int = R.layout.dialog_bottom

    override fun onCreate() {
        super.onCreate()
        findViewById<AppCompatTextView>(R.id.tv_cancel).clickNoRepeat {
            dismiss()
            callback?.invoke()
        }
    }
}