package com.tian.common.app.feature.permission

import android.annotation.SuppressLint
import android.app.Activity
import android.content.res.Configuration
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import com.hjq.permissions.OnPermissionCallback
import com.hjq.permissions.OnPermissionInterceptor
import com.hjq.permissions.OnPermissionPageCallback
import com.hjq.permissions.Permission
import com.hjq.permissions.PermissionFragment
import com.hjq.permissions.XXPermissions
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.core.BasePopupView
import com.lxj.xpopup.util.XPopupUtils
import com.tian.common.app.feature.dialog.style.PermissionApplyDialog
import com.tian.common.app.feature.dialog.style.PermissionExplainDialog

/**
 * 权限申请拦截器处理类
 */
class PermissionInterceptor(private val applyInfo: PermissionApplyInfo) : OnPermissionInterceptor {

    /** 弹窗描述延时 */
    private val handler: Handler = Handler(Looper.getMainLooper())

    /** 权限申请标记  */
    private var mRequestFlag = false

    /** 权限用途说明弹窗 */
    private var explainPopup: BasePopupView? = null

    /**
     * 发起权限申请（可在此处先弹 Dialog 再申请权限，如果用户已经授予权限，则不会触发此回调）
     *
     * Params:
     * allPermissions – 申请的权限
     * callback – 权限申请回调
     */
    @SuppressLint("ObsoleteSdkInt")
    override fun launchPermissionRequest(
        activity: Activity,
        allPermissions: MutableList<String>,
        callback: OnPermissionCallback?,
    ) {
        // 标记申请
        mRequestFlag = true
        // 获取没有授予的权限
        val deniedPermissions = XXPermissions.getDenied(activity, allPermissions)

        // 屏幕方向
        val activityOrientation = activity.resources.configuration.orientation
        // 权限介绍弹窗展示为竖屏
        var showDescPopup = activityOrientation == Configuration.ORIENTATION_PORTRAIT

        // 校验申请权限
        for (permission in allPermissions) {
            // 判断权限是否为特殊权限
            if (!XXPermissions.isSpecial(permission)) {
                continue
            }
            // 判断权限是否授予了
            if (XXPermissions.isGranted(activity, permission)) {
                continue
            }
            // 文件管理权限
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R &&
                TextUtils.equals(Permission.MANAGE_EXTERNAL_STORAGE, permission)
            ) {
                continue
            }
            // 如果申请的权限带有特殊权限，并且还没有授予的话，就不用 PopupWindow 对话框来显示，而是用 Dialog 来显示
            showDescPopup = false
            break
        }

        // 权限申请说明弹窗
        if (showDescPopup) {
            PermissionFragment.launch(activity, allPermissions, this, callback)
            // 延迟 300 毫秒是为了避免出现 PopupWindow 显示然后立马消失的情况
            // 因为框架没有办法在还没有申请权限的情况下，去判断权限是否永久拒绝了，必须要在发起权限申请之后
            // 所以只能通过延迟显示 PopupWindow 来做这件事，如果 300 毫秒内权限申请没有结束，证明本次申请的权限没有永久拒绝
            // 手动设置不显示权限用途说明则不弹出
            if (!applyInfo.isShowDescPopup) return
            handler.postDelayed({
                if (!mRequestFlag) {
                    return@postDelayed
                }
                if (activity.isFinishing ||
                    (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && activity.isDestroyed)
                ) {
                    return@postDelayed
                }
                showPermissionExplainPopup(activity, applyInfo.permissionDesc)
            }, 300)
        } else {
            XPopup.Builder(activity)
                .isDestroyOnDismiss(true)
                .hasStatusBar(false)
                .isRequestFocus(false)
                .asCustom(PermissionApplyDialog(
                    activity,
                    applyInfo.permissionDesc,
                    {
                        callback!!.onDenied(deniedPermissions, false)
                    }, {
                        PermissionFragment.launch(
                            activity, allPermissions,
                            this@PermissionInterceptor, callback
                        )
                    })
                )
                .show()
        }
    }

    /**
     * 用户拒绝了权限（注意需要在此处回调 OnPermissionCallback. onDenied(List, boolean)）
     *
     * Params:
     * allPermissions – 申请的权限
     * deniedPermissions – 已拒绝的权限
     * doNotAskAgain – 是否勾选了不再询问选项
     * callback – 权限申请回调
     */
    override fun deniedPermissionRequest(
        activity: Activity,
        allPermissions: MutableList<String>,
        deniedPermissions: MutableList<String>,
        doNotAskAgain: Boolean,
        callback: OnPermissionCallback?,
    ) {
        callback?.onDenied(deniedPermissions, doNotAskAgain)

        // 点击了不再询问
        if (doNotAskAgain) {
            showPermissionSettingDialog(activity, allPermissions, deniedPermissions, callback)
        }
    }

    /**
     * 权限请求完成
     *
     * Params:
     * allPermissions – 申请的权限
     * skipRequest – 是否跳过了申请过程
     * callback – 权限申请回调
     */
    override fun finishPermissionRequest(
        activity: Activity,
        allPermissions: MutableList<String>,
        skipRequest: Boolean,
        callback: OnPermissionCallback?,
    ) {
        mRequestFlag = false
        explainPopup?.dismiss()
    }

    /**
     * 顶部弹窗提示权限申请用途说明
     *
     * @param activity 绑定activity
     * @param desc     说明内容文本
     */
    private fun showPermissionExplainPopup(activity: Activity, desc: String) {
        if (explainPopup == null) {
            explainPopup = XPopup.Builder(activity)
                .hasShadowBg(false)
                .hasBlurBg(false)
                .isDestroyOnDismiss(true) //对于只使用一次的弹窗，推荐设置这个
                .isCenterHorizontal(true)
                .offsetY(XPopupUtils.getStatusBarHeight(null))
                .asCustom(PermissionExplainDialog(activity, desc))
        }
        explainPopup?.show()
    }

    /**
     * 勾选了不再询问，弹窗提示确认
     *
     * @param activity         承载activity
     * @param allPermissions    请求权限
     * @param deniedPermissions 已拒绝的权限
     * @param callback          权限申请回调
     */
    @SuppressLint("ObsoleteSdkInt")
    private fun showPermissionSettingDialog(
        activity: Activity?,
        allPermissions: List<String>,
        deniedPermissions:
        List<String>,
        callback: OnPermissionCallback?,
    ) {
        if (activity == null || activity.isFinishing ||
            (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && activity.isDestroyed)
        ) {
            return
        }

        // 确认弹窗
        XPopup.Builder(activity)
            .isDestroyOnDismiss(true)
            .hasStatusBar(true)
            .isRequestFocus(false)
            .asCustom(
                PermissionApplyDialog(
                    activity,
                    applyInfo.permissionDesc,
                    cancelCallback = {
                        callback!!.onDenied(deniedPermissions, false)
                    },
                    sureCallback = {
                        XXPermissions.startPermissionActivity(activity,
                            deniedPermissions, object : OnPermissionPageCallback {
                                override fun onGranted() {
                                    if (callback == null) {
                                        return
                                    }
                                    callback.onGranted(allPermissions, true)
                                }

                                override fun onDenied() {
                                    showPermissionSettingDialog(
                                        activity, allPermissions,
                                        XXPermissions.getDenied(activity, allPermissions), callback
                                    )
                                }
                            })
                    })
            )
            .show()
    }
}