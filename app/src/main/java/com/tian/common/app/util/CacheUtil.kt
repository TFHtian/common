package com.tian.common.app.util

import android.text.TextUtils
import com.tian.common.app.config.AppConstants
import com.tian.common.app.data.model.bean.UserInfo
import com.tian.lib_common.lib_util.KvUtil

object CacheUtil {

    /**
     * 获取保存的账户信息
     */
    fun getUser(): UserInfo? {
        val userStr = KvUtil.decodeString(AppConstants.KvKey.USER)
        return if (TextUtils.isEmpty(userStr)) {
            null
        } else {
            GsonUtil.gsonToBean(userStr, UserInfo::class.java)
        }
    }

    /**
     * 设置账户信息
     */
    fun setUser(userResponse: UserInfo?) {
        if (userResponse == null) {
            KvUtil.encode(AppConstants.KvKey.USER, "")
        } else {
            KvUtil.encode(AppConstants.KvKey.USER, GsonUtil.toJson(userResponse))
        }
    }

    /**
     * 首页是否开启获取指定文章
     */
    fun isNeedTop(): Boolean {
        return true
    }
}