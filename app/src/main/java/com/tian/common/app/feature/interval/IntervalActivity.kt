package com.tian.common.app.feature.interval

import android.os.Bundle
import com.tian.common.R
import com.tian.common.app.feature.interval.library.Interval
import com.tian.common.databinding.ActivityIntervalBinding
import com.tian.lib_common.lib_base.mvvm.activity.BaseVmDbActivity
import com.tian.lib_common.lib_base.mvvm.viewmodel.BaseViewModel
import com.tian.lib_common.lib_ext.clickNoRepeat
import com.tian.lib_common.lib_widget.titlebar.CommonTitleBar
import java.util.concurrent.TimeUnit

class IntervalActivity: BaseVmDbActivity<BaseViewModel, ActivityIntervalBinding>() {

    private lateinit var interval: Interval // 轮询器

    override fun onBindLayout(): Int = R.layout.activity_interval

    override fun attachTitleBarView(): CommonTitleBar = mBinding.appbar.titleBar

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        setBarTitle(getString(R.string.interval))
        // 自定义计数器个数的轮询器, 当[start]]比[end]值大, 且end不等于-1时, 即为倒计时
        interval = Interval(0, 1, TimeUnit.SECONDS, 20).life(this)
        // interval = Interval(1, TimeUnit.SECONDS) // 每秒回调一次, 不会自动结束
        interval.subscribe {
            mBinding.tvContent.text = it.toString()
        }.finish {
            mBinding.tvContent.text = getString(R.string.interval_complete)
        }.start()
    }

    override fun initListener() {
        mBinding.run {
            // 开始
            tvStart.clickNoRepeat {
                interval.start()
            }
            // 暂停
            tvPause.clickNoRepeat {
                interval.pause()
            }
            // 继续
            tvResume.clickNoRepeat {
                interval.resume()
            }
            // 重置
            tvReset.clickNoRepeat {
                interval.reset()
            }
            // 切换
            tvSwitch.clickNoRepeat {
                interval.switch()
            }
            // 结束
            tvStop.clickNoRepeat {
                interval.stop()
            }
            // 取消
            tvCancel.clickNoRepeat {
                interval.cancel()
            }
        }
    }

    override fun createObserver() {}
}