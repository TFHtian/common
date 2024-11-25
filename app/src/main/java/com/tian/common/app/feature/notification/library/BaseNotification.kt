package com.tian.common.app.feature.notification.library

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.tian.common.app.feature.notification.library.utils.ContextUtil
import com.tian.common.app.feature.notification.library.view.BaseRemoteViews
import com.tian.common.app.feature.notification.library.view.BigRemote
import com.tian.common.app.feature.notification.library.view.ContentRemote
import com.tian.common.app.feature.notification.library.view.CustomRemote
import com.tian.common.app.feature.notification.library.view.TickerRemote

abstract class BaseNotification<T : Any>(open var data: T) : IBaseNotify<T> {

    /**
     * Base上下文
     */
    private val mContext: Context = ContextUtil.getApplication()

    /**
     * Manager
     */
    private val mNotificationManagerCompat = NotificationControl.mNotificationManagerCompat

    /**
     *  通知栏构造器
     */
    private lateinit var mBuilder: NotificationCompat.Builder

    /**
     * 自定义视图管理类
     */
    private val mBaseRemoteViews: BaseRemoteViews = BaseRemoteViews()

    init {
        initChannel()
        initBuilder()
    }

    /**
     * 初始化通知渠道 让base渠道类继承并实现。同一个渠道不会二次执行配置
     */

    @SuppressLint("ObsoleteSdkInt")
    final override fun initChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //获取用户的渠道id
            var notificationChannel =
                mNotificationManagerCompat.getNotificationChannel(getChannelIdStr())

            if (notificationChannel == null) {

                //为用户配置默认渠道 让用户配置 并创建 如果渠道已经创建了，就不做任何操作 不重建渠道
                notificationChannel = NotificationChannel(
                    getChannelIdStr(),
                    getChannelName(),
                    NotificationManager.IMPORTANCE_DEFAULT
                )

                configureChannel(notificationChannel)

                mNotificationManagerCompat.createNotificationChannel(notificationChannel)
            }
        }
    }

    /**
     * 初始化通知
     */
    final override fun initBuilder() {
        mBuilder = NotificationCompat.Builder(mContext, getChannelIdStr())
        configureNotify(mBuilder)
    }

    /**
     * 获取srt类型的ChannelId 形式为 {你的app报名:getChannelId()}
     */
    private fun getChannelIdStr(): String {
        return mContext.packageName + ":" + getChannelId()
    }

    /**
     * 添加大图
     */
    protected fun addBigRemoteViews(layoutId: Int) {
        mBaseRemoteViews.bigRemote = BigRemote(layoutId)
    }

    /**
     * 添加小图
     */
    protected fun addContentRemoteViews(layoutId: Int) {
        mBaseRemoteViews.contentRemote = ContentRemote(layoutId)
    }

    /**
     * 添加TickerView
     */
    protected fun addTickerRemoteViews(tickerText: String, layoutId: Int) {
        mBaseRemoteViews.tickerRemote = TickerRemote(tickerText, layoutId)
    }

    /**
     * 添加自定义视图
     */
    protected fun addCustomContentRemoteView(layoutId: Int) {
        mBaseRemoteViews.customContentRemote = CustomRemote(layoutId)
    }

    /**
     * 显示
     */
    override fun show() {
        show(data)
    }

    /**
     * 显示 并配置数据
     */
    @SuppressLint("MissingPermission")
    override fun show(mData: T) {
        this.data = mData
        convert(mBaseRemoteViews, mData)
        configureRemote()
        mNotificationManagerCompat.notify(getNotificationId(), mBuilder.build())
    }

    /**
     * 显示 配置系统进度
     */
    @SuppressLint("MissingPermission")
    override fun showSystemProgress(
        context: CharSequence,
        max: Int,
        progress: Int,
        indeterminate: Boolean
    ) {
        mBuilder.setContentText(context).setProgress(max, progress, indeterminate)
        mNotificationManagerCompat.notify(getNotificationId(), mBuilder.build())
    }

    /**
     * 显示为前台通知
     * android.permission.FOREGROUND_SERVICE
     */
    override fun show(service: Service) {
        show(service, data)
    }

    /**
     * 显示为前台通知 并配置数据
     * android.permission.FOREGROUND_SERVICE
     */
    override fun show(service: Service, mData: T) {
        this.data = mData
        convert(mBaseRemoteViews, mData)
        configureRemote()
        service.startForeground(getNotificationId(), mBuilder.build())
    }

    /**
     * 显示为前台通知并设置 service type
     * android.permission.FOREGROUND_SERVICE
     */
    override fun show(service: Service, foregroundServiceType: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            show(service, foregroundServiceType, data)
        } else {
            show(service, data)
        }
    }

    /**
     * 显示为前台通知并设置 并配置数据
     * android.permission.FOREGROUND_SERVICE
     */
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun show(service: Service, foregroundServiceType: Int, mData: T) {
        this.data = mData
        convert(mBaseRemoteViews, mData)
        configureRemote()
        service.startForeground(getNotificationId(), mBuilder.build(), foregroundServiceType)
    }

    /**
     * 取消这个通知
     */
    override fun cancel() {
        mNotificationManagerCompat.cancel(getNotificationId())
    }

    /**
     * 配置视图
     */
    private fun configureRemote() {
        mBaseRemoteViews.customContentRemote?.let {
            mBuilder.setCustomContentView(it)
        }
        mBaseRemoteViews.contentRemote?.let {
            mBuilder.setContent(it)
        }
        mBaseRemoteViews.bigRemote?.let {
            mBuilder.setCustomBigContentView(it)
        }
        mBaseRemoteViews.tickerRemote?.let { tickerRemote ->
            tickerRemote.tickerText?.let { tickerText ->
                mBuilder.setTicker(tickerText, tickerRemote)
            }
        }
    }

    /**
     * 配置数据
     */
    abstract fun convert(mBaseRemoteViews: BaseRemoteViews, data: T)

    /**
     * 配置用户的渠道
     * @param
     */
    @SuppressLint("ObsoleteSdkInt")
    @RequiresApi(api = Build.VERSION_CODES.O)
    abstract fun configureChannel(notificationChannel: NotificationChannel)

    /**
     * 配置通知信息
     */
    abstract fun configureNotify(mBuilder: NotificationCompat.Builder)

    /**
     * 获取渠道名字 id已经默认为app的报名了
     */
    abstract fun getChannelName(): String

    /**
     * 获取通知id
     */
    abstract fun getNotificationId(): Int

    /**
     * 获取渠道id  最终类型为 {你的app报名:0} 的字符串
     */
    abstract fun getChannelId(): String
}