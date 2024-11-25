package com.tian.common.app.feature.notification.library.view

import com.tian.common.app.feature.notification.library.utils.ContextUtil

open class TickerRemote(var  tickerText: CharSequence?="", layoutId: Int) :
    AbRemoteViews(ContextUtil.getApplication().packageName, layoutId)