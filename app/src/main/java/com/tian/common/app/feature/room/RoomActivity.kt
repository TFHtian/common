package com.tian.common.app.feature.room

import android.os.Bundle
import com.tian.common.R
import com.tian.common.databinding.ActivityRoomBinding
import com.tian.lib_common.lib_base.mvvm.activity.BaseVmDbActivity
import com.tian.lib_common.lib_ext.clickNoRepeat
import com.tian.lib_common.lib_widget.titlebar.CommonTitleBar

class RoomActivity: BaseVmDbActivity<RoomViewModel, ActivityRoomBinding>() {

    override fun onBindLayout(): Int = R.layout.activity_room

    override fun attachTitleBarView(): CommonTitleBar = mBinding.appbar.titleBar

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        setBarTitle(getString(R.string.room))
    }

    override fun initListener() {
        // 插入数据
        mBinding.tvInsert.clickNoRepeat {
            mViewModel.insertRxStudent()
        }
        // 查询数据
        mBinding.tvQuery.clickNoRepeat {
            mViewModel.queryRxStudent()
        }
    }

    override fun createObserver() {
        mViewModel.studentContent.observe(this) {
            mBinding.tvContent.text = it
        }
    }
}