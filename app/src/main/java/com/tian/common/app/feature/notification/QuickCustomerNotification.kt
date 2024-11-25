package com.tian.common.app.feature.notification

import android.app.NotificationChannel
import android.provider.Settings
import android.text.TextUtils
import androidx.core.app.NotificationCompat
import com.tian.common.R
import com.tian.common.app.feature.notification.library.BaseNotification
import com.tian.common.app.feature.notification.library.ChannelConfig
import com.tian.common.app.feature.notification.library.view.BaseRemoteViews
import com.tian.common.app.feature.notification.library.view.BigRemote
import com.tian.common.app.feature.notification.library.view.ContentRemote
import com.tian.common.app.feature.notification.library.view.CustomRemote

class QuickCustomerNotification(data: QuickNotificationData): BaseNotification<QuickNotificationData>(data) {

    init {
        addBigRemoteViews(R.layout.layout_notification)
    }

    override fun convert(mBaseRemoteViews: BaseRemoteViews, data: QuickNotificationData) {
        val contentRemote: BigRemote? = mBaseRemoteViews.bigRemote
        contentRemote
            ?.setOnClickPendingIntent2(data.notifyId, R.id.tv_stop)
            ?.setTextViewText2(R.id.tv_content, data.customerContent)
            ?.setProgressBar2(R.id.progress_customer, data.progressMax, data.progressCurrent, false)
    }

    override fun configureChannel(notificationChannel: NotificationChannel) {
        notificationChannel.lockscreenVisibility = data.channel.lockScreenVisibility
        notificationChannel.importance = data.channel.importance
    }

    override fun configureNotify(mBuilder: NotificationCompat.Builder) {
        //设置通知的共同属性
        mBuilder.setShowWhen(true)
            .setPriority(ChannelConfig.getLowVersionPriority(data.channel)) // 通知优先级，优先级确定通知在Android7.1和更低版本上的干扰程度。
            .setVisibility(data.channel.lockScreenVisibility) // 锁定屏幕公开范围
            .setVibrate(data.channel.vibrate) // 震动模式
            .setSound(data.channel.sound ?: Settings.System.DEFAULT_NOTIFICATION_URI) // 声音
            .setAutoCancel(data.channel.autoCancel) // 设置通知栏点击后是否清除，设置为true，当点击此通知栏后，它会自动消失
            .setWhen(System.currentTimeMillis()) //通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
            .setOnlyAlertOnce(data.channel.onlyAlertOnce) // 设置通知只会在通知首次出现时打断用户（通过声音、振动或视觉提示），而之后更新则不会再打断用户。
            .setOngoing(data.channel.ongoing)// 设置是否是正在进行中的通知，默认是false
        // 图标
        if (data.icon != 0) mBuilder.setSmallIcon(data.icon)
        // 标题，此为可选内容
        if (!TextUtils.isEmpty(data.title)) mBuilder.setContentTitle(data.title).setTicker(data.title)
        // 正文文本，此为可选内容
        if (!TextUtils.isEmpty(data.content)) mBuilder.setContentText(data.content).setContentInfo(data.content)
    }

    override fun getChannelName(): String {
        return data.channelName
    }

    override fun getNotificationId(): Int {
        return data.notifyId
    }

    override fun getChannelId(): String {
        return data.channelName
    }
}