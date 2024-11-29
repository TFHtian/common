package com.tian.common.app.feature.permission

import android.os.Bundle
import com.tian.common.R
import com.tian.common.app.ext.applyPermission
import com.tian.common.app.ext.toast
import com.tian.common.databinding.ActivityPermissionBinding
import com.tian.lib_common.lib_base.mvvm.activity.BaseVmDbActivity
import com.tian.lib_common.lib_base.mvvm.viewmodel.BaseViewModel
import com.tian.lib_common.lib_ext.clickNoRepeat
import com.tian.lib_common.lib_widget.titlebar.CommonTitleBar

class PermissionActivity: BaseVmDbActivity<BaseViewModel, ActivityPermissionBinding>() {

    override fun onBindLayout(): Int = R.layout.activity_permission

    override fun attachTitleBarView(): CommonTitleBar = mBinding.appbar.titleBar

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        setBarTitle(getString(R.string.permission))
    }

    override fun initListener() {
        mBinding.run {
            // 申请相机权限
            tvCamera.clickNoRepeat {
                applyPermission(this@PermissionActivity, PermissionConfig.CAMERA, {
                    toast(getString(R.string.permission_apply_success))
                })
            }

            // 申请定位权限
            tvLocation.clickNoRepeat {
                applyPermission(this@PermissionActivity, PermissionConfig.LOCATION, {
                    toast(getString(R.string.permission_apply_success))
                }, {
                    toast(getString(R.string.permission_apply_failed))
                })
            }

            // 申请蓝牙权限
            tvBluetooth.clickNoRepeat {
                applyPermission(this@PermissionActivity, PermissionConfig.BLUETOOTH)
            }

            // 申请通知权限
            tvNotification.clickNoRepeat {
                applyPermission(this@PermissionActivity, PermissionConfig.NOTIFICATION)
            }

            // 申请安装包权限
            tvInstall.clickNoRepeat {
                applyPermission(this@PermissionActivity, PermissionConfig.INSTALL)
            }

            // 申请WIFI权限
            tvWifi.clickNoRepeat {
                applyPermission(this@PermissionActivity, PermissionConfig.WIFI)
            }

            // 申请麦克风权限
            tvAudio.clickNoRepeat {
                applyPermission(this@PermissionActivity, PermissionConfig.AUDIO)
            }

            // 申请文件权限
            tvStorage.clickNoRepeat {
                applyPermission(this@PermissionActivity, PermissionConfig.STORAGE)
            }

            // 申请媒体权限
            tvMedia.clickNoRepeat {
                applyPermission(this@PermissionActivity, PermissionConfig.MEDIA)
            }
        }
    }

    override fun createObserver() {}
}