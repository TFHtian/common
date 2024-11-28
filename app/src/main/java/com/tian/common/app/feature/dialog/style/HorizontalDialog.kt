package com.tian.common.app.feature.dialog.style

import android.content.Context
import com.lxj.xpopup.core.HorizontalAttachPopupView
import com.tian.common.R

class HorizontalDialog(context: Context): HorizontalAttachPopupView(context) {

    override fun getImplLayoutId(): Int = R.layout.dialog_horizontal

    override fun onCreate() {
        super.onCreate()
    }
}