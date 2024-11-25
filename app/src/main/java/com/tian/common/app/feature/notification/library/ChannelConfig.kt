package com.tian.common.app.feature.notification.library

import android.app.NotificationManager
import android.net.Uri
import androidx.core.app.NotificationCompat
import com.tian.common.R
import com.tian.common.app.App

object ChannelConfig {

    /** 通知渠道-系统通知(重要性级别-中：无提示音) */
    val NOTICE = Channel(
        importance = NotificationManager.IMPORTANCE_LOW
    )

    /** 通知渠道-@提醒消息(重要性级别-紧急：发出提示音，并以浮动通知的形式显示 & 锁屏显示 & 振动0.25s )*/
    val MENTION = Channel(
        importance = NotificationManager.IMPORTANCE_HIGH,
        lockScreenVisibility = NotificationCompat.VISIBILITY_PUBLIC,
        vibrate = longArrayOf(0, 250)
    )

    /** 通知渠道-音视频通话(重要性级别-紧急：发出提示音，并以浮动通知的形式显示 & 锁屏显示 & 振动4s停2s再振动4s ) */
    val CALL = Channel(
        importance = NotificationManager.IMPORTANCE_HIGH,
        lockScreenVisibility = NotificationCompat.VISIBILITY_PUBLIC,
        vibrate = longArrayOf(0, 4000, 2000, 4000),
        sound = Uri.parse("android.resource://" + App.instance.packageName + "/" + R.raw.iphone)
    )

    fun getLowVersionPriority(channel: Channel): Int {
        return when (channel.importance) {
            NotificationManager.IMPORTANCE_HIGH -> NotificationCompat.PRIORITY_HIGH
            NotificationManager.IMPORTANCE_LOW -> NotificationCompat.PRIORITY_LOW
            NotificationManager.IMPORTANCE_MIN -> NotificationCompat.PRIORITY_MIN
            else -> NotificationCompat.PRIORITY_DEFAULT
        }
    }
}

/**
 * 通知渠道
 */
data class Channel(
    val importance: Int = NotificationManager.IMPORTANCE_DEFAULT,  // 重要性级别
    val priority: Int = NotificationCompat.PRIORITY_DEFAULT, // 通知优先级
    @NotificationCompat.NotificationVisibility
    val lockScreenVisibility: Int = NotificationCompat.VISIBILITY_SECRET,        // 锁定屏幕公开范围
    val vibrate: LongArray? = null,                                              // 震动模式
    val sound: Uri? = null,                                                      // 声音
    val autoCancel: Boolean = true,                                              //是否支持取消
    val onlyAlertOnce: Boolean = true,                                           // 设置通知只会在通知首次出现时打断用户（通过声音、振动或视觉提示），而之后更新则不会再打断用户。
    val ongoing: Boolean = false                                                 //是否是正在进行中的通知，默认是false
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Channel

        if (vibrate != null) {
            if (other.vibrate == null) return false
            if (!vibrate.contentEquals(other.vibrate)) return false
        } else if (other.vibrate != null) return false

        return true
    }

    override fun hashCode(): Int {
        return vibrate?.contentHashCode() ?: 0
    }
}