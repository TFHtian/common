package com.tian.common.app.feature.dialog.style

import android.content.Context
import com.lxj.xpopup.core.BubbleHorizontalAttachPopupView
import com.lxj.xpopup.util.XPopupUtils
import com.tian.common.R

class HorizontalBubbleDialog(context: Context): BubbleHorizontalAttachPopupView(context) {

    override fun getImplLayoutId(): Int = R.layout.dialog_horizontal_bubble

    override fun onCreate() {
        super.onCreate()
        setBubbleBgColor(context.getColor(R.color.white))
        setBubbleRadius(XPopupUtils.dp2px(context, 4f))
        setBubbleShadowSize(XPopupUtils.dp2px(context, 4f))
        setBubbleShadowColor(context.getColor(R.color.color_333333))
        setArrowWidth(XPopupUtils.dp2px(context, 8f))
        setArrowHeight(XPopupUtils.dp2px(context, 8f))
    }
}