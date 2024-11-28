package com.tian.common.app.feature.dialog.style

import android.content.Context
import com.lxj.xpopup.core.PositionPopupView
import com.tian.common.R

class PositionDialog(context: Context): PositionPopupView(context) {

    override fun getImplLayoutId(): Int = R.layout.dialog_position

    override fun onCreate() {
        super.onCreate()
    }
}