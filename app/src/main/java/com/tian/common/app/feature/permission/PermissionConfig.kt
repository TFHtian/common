package com.tian.common.app.feature.permission

import com.hjq.permissions.Permission
import com.tian.common.R
import com.tian.common.app.App

object PermissionConfig {

    /** 相机权限 */
    val CAMERA = PermissionApplyInfo(
        permissions = arrayListOf(
            Permission.CAMERA
        ),
        isShowDescPopup = true,
        permissionDesc = App.instance.getString(R.string.permission_camera_desc)
    )

    /** 定位权限 */
    val LOCATION = PermissionApplyInfo(
        permissions = arrayListOf(
            Permission.ACCESS_COARSE_LOCATION,
            Permission.ACCESS_FINE_LOCATION,
            // 如果不需要在后台使用定位功能，请不要申请此权限
            Permission.ACCESS_BACKGROUND_LOCATION
        ),
        isShowDescPopup = true,
        permissionDesc = App.instance.getString(R.string.permission_location_desc)
    )

    /** 蓝牙权限 */
    val BLUETOOTH = PermissionApplyInfo(
        permissions = arrayListOf(
            Permission.BLUETOOTH_SCAN,
            Permission.BLUETOOTH_CONNECT,
            Permission.BLUETOOTH_ADVERTISE
        ),
        permissionDesc = App.instance.getString(R.string.permission_description)
    )

    /** 通知权限 */
    val NOTIFICATION = PermissionApplyInfo(
        permissions = arrayListOf(
            Permission.POST_NOTIFICATIONS
        ),
        permissionDesc = App.instance.getString(R.string.permission_description)
    )

    /** 安装权限 */
    val INSTALL = PermissionApplyInfo(
        permissions = arrayListOf(
            Permission.GET_INSTALLED_APPS
        ),
        permissionDesc = App.instance.getString(R.string.permission_description)
    )

    /** WIFI权限 */
    val WIFI = PermissionApplyInfo(
        permissions = arrayListOf(
            Permission.NEARBY_WIFI_DEVICES
        ),
        permissionDesc = App.instance.getString(R.string.permission_description)
    )

    /** 麦克风权限 */
    val AUDIO = PermissionApplyInfo(
        permissions = arrayListOf(
            Permission.RECORD_AUDIO
        ),
        permissionDesc = App.instance.getString(R.string.permission_description)
    )

    /** 文件权限 */
    val STORAGE = PermissionApplyInfo(
        permissions = arrayListOf(
            Permission.MANAGE_EXTERNAL_STORAGE
        ),
        permissionDesc = App.instance.getString(R.string.permission_description)
    )

    /** 媒体权限 */
    val MEDIA = PermissionApplyInfo(
        permissions = arrayListOf(
            Permission.READ_MEDIA_IMAGES,
            Permission.READ_MEDIA_VIDEO,
            Permission.READ_MEDIA_AUDIO,
            Permission.READ_MEDIA_VISUAL_USER_SELECTED
        ),
        permissionDesc = App.instance.getString(R.string.permission_description)
    )
}

/**
 * 申请权限信息
 */
data class PermissionApplyInfo(
    val permissions: ArrayList<String> = arrayListOf(),// 申请的权限
    val isShowDescPopup: Boolean = false, // 是否弹窗提示权限申请用途说明
    val permissionDesc: String = "", // 权限用途说明文本
)