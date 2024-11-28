package com.tian.common.app.feature.dialog.style

import android.content.Context
import androidx.databinding.DataBindingUtil
import com.lxj.xpopup.core.BubbleAttachPopupView
import com.lxj.xpopup.util.XPopupUtils
import com.tian.common.R
import com.tian.common.databinding.DialogVerticalBubbleBinding

class VerticalBubbleDialog(context: Context): BubbleAttachPopupView(context) {

    private var mBinding: DialogVerticalBubbleBinding? = null

    override fun getImplLayoutId(): Int = R.layout.dialog_vertical_bubble

    override fun onCreate() {
        super.onCreate()
        mBinding = DataBindingUtil.bind(popupImplView)
        setBubbleBgColor(context.getColor(com.tian.lib_common.R.color.base_theme))
        setBubbleRadius(XPopupUtils.dp2px(context, 4f))
        setBubbleShadowSize(XPopupUtils.dp2px(context, 4f))
        setBubbleShadowColor(context.getColor(R.color.color_333333))
        setArrowWidth(XPopupUtils.dp2px(context, 8f))
        setArrowHeight(XPopupUtils.dp2px(context, 8f))
    }
}