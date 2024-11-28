package com.tian.common.app.feature.dialog.style

import android.content.Context
import com.lxj.xpopup.core.DrawerPopupView
import com.lxj.xpopup.util.XPopupUtils
import com.tian.common.R

class SideDrawDialog(context: Context): DrawerPopupView(context) {

    override fun getImplLayoutId(): Int = R.layout.dialog_full_screen

    override fun onCreate() {
        super.onCreate()
    }

    override fun getPopupWidth(): Int {
        return (XPopupUtils.getAppWidth(context) * 0.75f).toInt()
    }
}