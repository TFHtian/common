package com.tian.common.app.feature.notification

import android.graphics.Bitmap
import com.tian.common.app.feature.notification.library.Channel

data class QuickNotificationData(
    val icon: Int,
    val channelName: String,
    val notifyId: Int,
    val channel: Channel = Channel(),
    val title: CharSequence? = null,
    val content: CharSequence? = null,
    val customerContent: CharSequence? = null,
    val progressMax: Int = 0,
    val progressCurrent: Int = 0,
    val bigText: CharSequence? = null,
    val bigPic: Bitmap? = null
)