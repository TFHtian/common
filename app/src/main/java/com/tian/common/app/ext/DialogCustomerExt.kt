package com.tian.common.app.ext

import android.content.Context
import android.view.View
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.enums.PopupAnimation
import com.lxj.xpopup.enums.PopupPosition
import com.tian.common.app.feature.dialog.style.BottomDialog
import com.tian.common.app.feature.dialog.style.CenterDialog
import com.tian.common.app.feature.dialog.style.DropDownDialog
import com.tian.common.app.feature.dialog.style.FullScreenDialog
import com.tian.common.app.feature.dialog.style.HorizontalBubbleDialog
import com.tian.common.app.feature.dialog.style.HorizontalDialog
import com.tian.common.app.feature.dialog.style.PositionDialog
import com.tian.common.app.feature.dialog.style.SideDrawDialog
import com.tian.common.app.feature.dialog.style.SoftTopDialog
import com.tian.common.app.feature.dialog.style.TopDialog
import com.tian.common.app.feature.dialog.style.VerticalBubbleDialog
import com.tian.common.app.feature.dialog.style.VerticalDialog

/**
 * 顶部弹出弹窗
 */
fun showTopDialog(context: Context, callback: (() -> Unit)? = null) {
    XPopup.Builder(context)
        .isDestroyOnDismiss(true)
        .popupAnimation(PopupAnimation.TranslateFromTop)
        .asCustom(TopDialog(context) {
            callback?.invoke()
        })
        .show()
}

/**
 * 居中弹出弹窗
 */
fun showCenterDialog(context: Context, sureCallback: ((result: String) -> Unit)? = null) {
    XPopup.Builder(context)
        .isDestroyOnDismiss(true)
        .hasStatusBar(false)
        .isRequestFocus(false)
        .asCustom(CenterDialog(context, sureCallback = {
            sureCallback?.invoke(it)
        }))
        .show()
}

/**
 * 底部弹出弹出
 */
fun showBottomDialog(context: Context, callback: (() -> Unit)? = null) {
    XPopup.Builder(context)
        .isDestroyOnDismiss(true)
        .asCustom(BottomDialog(context) {
            callback?.invoke()
        })
        .show()
}

/**
 * 水平弹窗
 */
fun showHorizontalDialog(context: Context, view: View) {
    XPopup.Builder(context)
        .isDestroyOnDismiss(true)
        .hasShadowBg(false)
        .atView(view)
        .offsetX(10)
        .asCustom(HorizontalDialog(context))
        .show()
}

/**
 * 水平气泡弹窗
 */
fun showHorizontalBubbleDialog(context: Context, view: View) {
    XPopup.Builder(context)
        .isDestroyOnDismiss(true)
        .hasShadowBg(false)
        .atView(view)
        .isCenterHorizontal(true)
        .asCustom(HorizontalBubbleDialog(context))
        .show()
}

/**
 * 垂直弹窗
 */
fun showVerticalDialog(context: Context, view: View) {
    XPopup.Builder(context)
        .isDestroyOnDismiss(true)
        .hasShadowBg(false)
        .offsetY(15)
        .atView(view)
        .asCustom(VerticalDialog(context))
        .show()
}

/**
 * 垂直气泡弹出
 */
fun showVerticalBubbleDialog(context: Context, view: View) {
    XPopup.Builder(context)
        .isDestroyOnDismiss(true)
        .hasShadowBg(false)
        .atView(view)
        .asCustom(VerticalBubbleDialog(context))
        .show()
}

/**
 * 全屏弹出
 */
fun showFullScreenDialog(context: Context) {
    XPopup.Builder(context)
        .isDestroyOnDismiss(true)
        .hasShadowBg(false)
        .isLightStatusBar(true)
        .asCustom(FullScreenDialog(context))
        .show()
}

/**
 * 软键盘之上
 */
fun showSoftTopDialog(context: Context) {
    XPopup.Builder(context)
        .autoOpenSoftInput(true)
        .isDestroyOnDismiss(true)
        .asCustom(SoftTopDialog(context))
        .show()
}

/**
 * 自由定位
 */

fun showPositionDialog(context: Context) {
    XPopup.Builder(context)
        .hasShadowBg(true)
        .hasBlurBg(false)
        .isDestroyOnDismiss(true) //对于只使用一次的弹窗，推荐设置这个
        .isCenterHorizontal(true)
        .offsetY(200)
        .asCustom(PositionDialog(context))
        .show()
}

/**
 * 下拉
 */
fun showDropDownDialog(context: Context, view: View) {
    XPopup.Builder(context)
        .atView(view)
        .isDestroyOnDismiss(true)
        .asCustom(DropDownDialog(context))
        .show()
}

/**
 * 侧滑
 */
fun showSideDrawDialog(context: Context) {
    XPopup.Builder(context)
        .isDestroyOnDismiss(true)
        .hasStatusBarShadow(true)
        .popupPosition(PopupPosition.Right)
        .asCustom(SideDrawDialog(context))
        .show()
}