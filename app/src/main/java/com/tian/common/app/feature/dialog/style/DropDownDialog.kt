package com.tian.common.app.feature.dialog.style

import android.content.Context
import com.lxj.xpopup.impl.PartShadowPopupView
import com.tian.common.R

class DropDownDialog(context: Context): PartShadowPopupView(context) {

    override fun getImplLayoutId(): Int = R.layout.dialog_top

    override fun onCreate() {
        super.onCreate()
    }
}