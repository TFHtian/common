package com.tian.common.app.feature.notification

import android.app.NotificationChannel
import android.provider.Settings
import android.text.TextUtils
import androidx.core.app.NotificationCompat
import com.tian.common.app.feature.notification.library.BaseNotification
import com.tian.common.app.feature.notification.library.ChannelConfig
import com.tian.common.app.feature.notification.library.view.BaseRemoteViews

class QuickSystemNotification(data: QuickNotificationData): BaseNotification<QuickNotificationData>(data) {

    override fun convert(mBaseRemoteViews: BaseRemoteViews, data: QuickNotificationData) {}

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
        // 进度条通知
        if (data.progressMax != 0) mBuilder.setProgress(data.progressMax, data.progressCurrent, false)
        // 大文本通知
        if (!TextUtils.isEmpty(data.bigText)) mBuilder.setStyle(NotificationCompat.BigTextStyle().bigText(data.bigText))
        // 大图片通知
        if (data.bigPic != null) mBuilder.setStyle(NotificationCompat.BigPictureStyle().bigPicture(data.bigPic))
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