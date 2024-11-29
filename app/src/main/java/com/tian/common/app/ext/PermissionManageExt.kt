package com.tian.common.app.ext

import android.content.Context
import com.hjq.permissions.OnPermissionCallback
import com.hjq.permissions.XXPermissions
import com.tian.common.app.feature.permission.PermissionApplyInfo
import com.tian.common.app.feature.permission.PermissionInterceptor

fun applyPermission(
    context: Context,
    applyInfo: PermissionApplyInfo,
    success: (() -> Unit)? = null,
    failed: (() -> Unit)? = null,
) {
    XXPermissions.with(context)
        .permission(applyInfo.permissions)
        .interceptor(PermissionInterceptor(applyInfo))
        .request(object : OnPermissionCallback{
            override fun onGranted(permissions: MutableList<String>, allGranted: Boolean) {
                if (allGranted) {
                    success?.invoke()
                } else{
                    failed?.invoke()
                }
            }

            override fun onDenied(permissions: MutableList<String>, doNotAskAgain: Boolean) {
                super.onDenied(permissions, doNotAskAgain)
                if (!doNotAskAgain) {
                    failed?.invoke()
                }
            }
        })
}