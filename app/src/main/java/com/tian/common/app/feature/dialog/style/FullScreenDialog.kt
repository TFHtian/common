package com.tian.common.app.feature.dialog.style

import android.content.Context
import com.lxj.xpopup.impl.FullScreenPopupView
import com.tian.common.R

class FullScreenDialog(context: Context): FullScreenPopupView(context) {

    override fun getImplLayoutId(): Int = R.layout.dialog_full_screen

    override fun onCreate() {
        super.onCreate()
    }
}