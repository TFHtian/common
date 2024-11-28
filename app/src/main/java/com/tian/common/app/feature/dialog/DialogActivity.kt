package com.tian.common.app.feature.dialog

import android.os.Bundle
import com.tian.common.R
import com.tian.common.app.ext.showBottomDialog
import com.tian.common.app.ext.showCenterDialog
import com.tian.common.app.ext.showDropDownDialog
import com.tian.common.app.ext.showFullScreenDialog
import com.tian.common.app.ext.showHorizontalBubbleDialog
import com.tian.common.app.ext.showHorizontalDialog
import com.tian.common.app.ext.showPositionDialog
import com.tian.common.app.ext.showSideDrawDialog
import com.tian.common.app.ext.showSoftTopDialog
import com.tian.common.app.ext.showTopDialog
import com.tian.common.app.ext.showVerticalBubbleDialog
import com.tian.common.app.ext.showVerticalDialog
import com.tian.common.databinding.ActivityDialogBinding
import com.tian.lib_common.lib_base.mvvm.activity.BaseVmDbActivity
import com.tian.lib_common.lib_base.mvvm.viewmodel.BaseViewModel
import com.tian.lib_common.lib_ext.clickNoRepeat
import com.tian.lib_common.lib_ext.logd
import com.tian.lib_common.lib_widget.titlebar.CommonTitleBar

class DialogActivity: BaseVmDbActivity<BaseViewModel, ActivityDialogBinding>() {

    override fun onBindLayout(): Int = R.layout.activity_dialog

    override fun attachTitleBarView(): CommonTitleBar = mBinding.appbar.titleBar

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        setBarTitle(getString(R.string.dialog))
    }

    override fun initListener() {
        mBinding.run {
            // 顶部弹出
            tvTop.clickNoRepeat {
                showTopDialog(this@DialogActivity)
            }
            // 居中弹出
            tvCenter.clickNoRepeat {
                showCenterDialog(this@DialogActivity) {
                    it.logd()
                }
            }
            // 底部弹出
            tvBottom.clickNoRepeat {
                showBottomDialog(this@DialogActivity)
            }
            // 水平
            tvAttachHorizontal.clickNoRepeat { view ->
                showHorizontalDialog(this@DialogActivity, view)
            }
            // 水平气泡
            tvAttachBubbleHorizontal.clickNoRepeat {
                showHorizontalBubbleDialog(this@DialogActivity,it)
            }
            // 垂直
            tvAttachVertical.clickNoRepeat {
                showVerticalDialog(this@DialogActivity,it)
            }
            // 垂直气泡
            tvAttachBubbleVertical.clickNoRepeat {
                showVerticalBubbleDialog(this@DialogActivity,it)
            }
            // 全屏
            tvFullScreen.clickNoRepeat {
                showFullScreenDialog(this@DialogActivity)
            }
            // 软键盘之上
            tvSoftTop.clickNoRepeat {
                showSoftTopDialog(this@DialogActivity)
            }
            // 自由定位
            tvPosition.clickNoRepeat {
                showPositionDialog(this@DialogActivity)
            }
            // 下拉
            tvDropDown.clickNoRepeat {
                showDropDownDialog(this@DialogActivity,it)
            }
            // 侧滑
            tvSideDraw.clickNoRepeat {
                showSideDrawDialog(this@DialogActivity)
            }
        }
    }

    override fun createObserver() {}
}