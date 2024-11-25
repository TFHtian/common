package com.tian.common.app.feature.notification.library

interface BaseNotificationData {

    fun getSmallIcon(): Int

    fun getContentTitle(): CharSequence?

    fun getContentText(): CharSequence?
}