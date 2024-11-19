package com.tian.common.app.feature.keystore.security

import android.app.Application
import android.content.Context
import com.tian.common.BuildConfig
import com.tian.common.app.feature.keystore.security.impl.AndroidKeyStoreSecureCryptoImpl

/**
 * 安全加密工具配置入口
 */
object SecureCryptoConfig {

    private lateinit var context: Application

    internal val appContext: Context get() {
        return try {
            context
        } catch (tr: UninitializedPropertyAccessException) {
            throw UninitializedPropertyAccessException("必须先调用 SecureCryptoConfig.setAppContext", tr)
        }
    }

    private var debugable = BuildConfig.DEBUG

    @JvmStatic
    fun setAppContext(context: Context) {
        SecureCryptoConfig.context = context.applicationContext as Application
    }

    /**
     * 是否允许输出 Debug 信息。默认为 [BuildConfig.DEBUG]
     */
    @JvmStatic
    fun setDebugable(debugable: Boolean): SecureCryptoConfig {
        SecureCryptoConfig.debugable = debugable
        return SecureCryptoConfig
    }

    internal fun log(log: () -> Unit) {
        if (debugable) {
            log()
        }
    }

    /**
     * 自定义安全加密实现类
     *
     * @param secureCryptoImpl 安全加密自定义实现。默认值：[AndroidKeyStoreSecureCryptoImpl]
     */
    @JvmStatic
    @JvmOverloads
    fun setSecureCryptoImpl(secureCryptoImpl: SecureCryptoInterface = AndroidKeyStoreSecureCryptoImpl()) {
        SecureCrypto.secureCryptoImpl = secureCryptoImpl
    }
}