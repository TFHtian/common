package com.tian.common.app.feature.notification

import android.graphics.BitmapFactory
import android.os.Bundle
import com.tian.common.R
import com.tian.common.app.feature.interval.library.Interval
import com.tian.common.app.feature.notification.library.ChannelConfig
import com.tian.common.app.feature.notification.library.NotificationControl
import com.tian.common.app.feature.notification.library.listener.PendingIntentListener
import com.tian.common.databinding.ActivityNotificationBinding
import com.tian.lib_common.lib_base.mvvm.activity.BaseVmDbActivity
import com.tian.lib_common.lib_base.mvvm.viewmodel.BaseViewModel
import com.tian.lib_common.lib_ext.clickNoRepeat
import com.tian.lib_common.lib_widget.titlebar.CommonTitleBar
import java.util.concurrent.TimeUnit

class NotificationActivity: BaseVmDbActivity<BaseViewModel, ActivityNotificationBinding>() ,
    PendingIntentListener {

    private var systemNotification: QuickSystemNotification? = null
    private var customerNotification: QuickCustomerNotification? = null

    override fun onBindLayout(): Int = R.layout.activity_notification

    override fun attachTitleBarView(): CommonTitleBar = mBinding.appbar.titleBar

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        setBarTitle(getString(R.string.notification))
    }

    override fun initListener() {
        // 普通通知
        mBinding.tvNormal.clickNoRepeat {
            systemNotification = null
            val normalData = QuickNotificationData(
                R.mipmap.ic_launcher,
                notifyId = QuickNotificationConstant.NOTIFY_NORMAL_ID,
                channelName = QuickNotificationConstant.NOTIFY_NORMAL_NAME,
                channel = ChannelConfig.NOTICE,
                title = getString(R.string.notification_normal)
                        + getString(R.string.notification_title)
                        + QuickNotificationConstant.NOTIFY_NORMAL_ID,
                content = getString(R.string.notification_normal)
                        + getString(R.string.notification_content)
                        + QuickNotificationConstant.NOTIFY_NORMAL_ID
            )
            if (systemNotification == null) {
                systemNotification = QuickSystemNotification(normalData)
                systemNotification?.show()
                NotificationControl.addPendingIntentListener(this)
            } else {
                systemNotification?.show(normalData)
            }
        }

        // 重要通知
        mBinding.tvImportance.clickNoRepeat {
            systemNotification = null
            val importanceData = QuickNotificationData(
                R.mipmap.ic_launcher,
                notifyId = QuickNotificationConstant.NOTIFY_IMPORTANCE_ID,
                channelName = QuickNotificationConstant.NOTIFY_IMPORTANCE_NAME,
                channel = ChannelConfig.MENTION,
                title = getString(R.string.notification_importance)
                        + getString(R.string.notification_title)
                        + QuickNotificationConstant.NOTIFY_IMPORTANCE_ID,
                content = getString(R.string.notification_importance)
                        + getString(R.string.notification_content)
                        + QuickNotificationConstant.NOTIFY_IMPORTANCE_ID
            )
            if (systemNotification == null) {
                systemNotification = QuickSystemNotification(importanceData)
                systemNotification?.show()
                NotificationControl.addPendingIntentListener(this)
            } else {
                systemNotification?.show(importanceData)
            }
        }

        // 进度条通知
        mBinding.tvProgress.clickNoRepeat {
            systemNotification = null
            val progressMax = 100
            var progressCurrent = 0
            val progressData = QuickNotificationData(
                R.mipmap.ic_launcher,
                notifyId = QuickNotificationConstant.NOTIFY_PROGRESS_ID,
                channelName = QuickNotificationConstant.NOTIFY_PROGRESS_NAME,
                channel = ChannelConfig.MENTION,
                title = getString(R.string.notification_progress)
                        + getString(R.string.notification_title)
                        + QuickNotificationConstant.NOTIFY_PROGRESS_ID,
                content = getString(R.string.notification_progress)
                        + getString(R.string.notification_content)
                        + QuickNotificationConstant.NOTIFY_PROGRESS_ID
                        + " $progressCurrent%",
                progressMax = progressMax,
                progressCurrent = progressCurrent
            )
            if (systemNotification == null) {
                systemNotification = QuickSystemNotification(progressData)
                systemNotification?.show()
                NotificationControl.addPendingIntentListener(this)
            } else {
                systemNotification?.show(progressData)
            }
            // 更新进度
            val interval = Interval(0, 1, TimeUnit.SECONDS, 100).life(this)
            interval.subscribe {
                progressCurrent = 100 - it.toInt()
                systemNotification?.showSystemProgress(
                    getString(R.string.notification_progress)
                            + getString(R.string.notification_content)
                            + QuickNotificationConstant.NOTIFY_PROGRESS_ID
                            + " $progressCurrent%",
                    progressMax,
                    progressCurrent,
                    false
                )
            }.finish {
                systemNotification?.cancel()
            }.start()
        }

        // 大文本通知
        mBinding.tvBigText.clickNoRepeat {
            systemNotification = null
            val bigTextData = QuickNotificationData(
                R.mipmap.ic_launcher,
                notifyId = QuickNotificationConstant.NOTIFY_BIG_TEXT_ID,
                channelName = QuickNotificationConstant.NOTIFY_BIG_TEXT_NAME,
                channel = ChannelConfig.MENTION,
                title = getString(R.string.notification_big_text)
                        + getString(R.string.notification_title)
                        + QuickNotificationConstant.NOTIFY_BIG_TEXT_ID,
                bigText = getString(R.string.notification_big_content)
            )
            if (systemNotification == null) {
                systemNotification = QuickSystemNotification(bigTextData)
                systemNotification?.show()
                NotificationControl.addPendingIntentListener(this)
            } else {
                systemNotification?.show(bigTextData)
            }
        }

        // 大图片通知
        mBinding.tvBigPicture.clickNoRepeat {
            systemNotification = null
            val bigPictureData = QuickNotificationData(
                R.mipmap.ic_launcher,
                notifyId = QuickNotificationConstant.NOTIFY_BIG_PICTURE_ID,
                channelName = QuickNotificationConstant.NOTIFY_BIG_PICTURE_NAME,
                channel = ChannelConfig.MENTION,
                title = getString(R.string.notification_big_picture)
                        + getString(R.string.notification_title)
                        + QuickNotificationConstant.NOTIFY_BIG_PICTURE_ID,
                content = getString(R.string.notification_big_picture)
                        + getString(R.string.notification_content)
                        + QuickNotificationConstant.NOTIFY_BIG_PICTURE_ID,
                bigPic = BitmapFactory.decodeResource(resources, R.drawable.ic_big_pic)
            )
            if (systemNotification == null) {
                systemNotification = QuickSystemNotification(bigPictureData)
                systemNotification?.show()
                NotificationControl.addPendingIntentListener(this)
            } else {
                systemNotification?.show(bigPictureData)
            }
        }

        // 自定义通知
        mBinding.tvCustomer.clickNoRepeat {
            val customerData = QuickNotificationData(
                R.mipmap.ic_launcher,
                notifyId = QuickNotificationConstant.NOTIFY_CUSTOMER_ID,
                channelName = QuickNotificationConstant.NOTIFY_CUSTOMER_NAME,
                channel = ChannelConfig.NOTICE,
                title = getString(R.string.notification_customer_title)
                        + QuickNotificationConstant.NOTIFY_CUSTOMER_ID,
                customerContent = getString(R.string.notification_customer_content),
                progressMax = 100,
                progressCurrent = 0
            )
            if (customerNotification == null) {
                customerNotification = QuickCustomerNotification(customerData)
                customerNotification?.show()
                NotificationControl.addPendingIntentListener(this)
            } else {
                customerNotification?.show(customerData)
            }

            // 更新进度
            val interval = Interval(0, 2, TimeUnit.SECONDS, 100).life(this)
            interval.subscribe {
                val prsCurrent = 100 - it.toInt()
                val cusContent = getString(R.string.notification_customer_content) + it.toInt()
                val cusData = QuickNotificationData(
                    R.mipmap.ic_launcher,
                    notifyId = QuickNotificationConstant.NOTIFY_CUSTOMER_ID,
                    channelName = QuickNotificationConstant.NOTIFY_CUSTOMER_NAME,
                    channel = ChannelConfig.NOTICE,
                    title = getString(R.string.notification_customer_title)
                            + QuickNotificationConstant.NOTIFY_CUSTOMER_ID,
                    customerContent = cusContent,
                    progressMax = 100,
                    progressCurrent = prsCurrent
                )
                customerNotification?.show(cusData)
            }.finish {
                systemNotification?.cancel()
            }.start()
        }
    }

    override fun createObserver() {}

    override fun onClick(notifyId: Int, viewId: Int) {

    }
}