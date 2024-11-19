package com.tian.common.app.util

import android.content.Context
import android.util.Base64
import com.tian.common.app.feature.keystore.security.SecureCrypto
import com.tian.common.app.feature.keystore.security.SecureCryptoConfig
import com.tian.lib_common.lib_ext.logd

object KeyStoreHelper {

    fun init(context: Context) {
        context.also {
            SecureCryptoConfig.setAppContext(it)
            SecureCryptoConfig.setDebugable(true)
        }
    }

    fun encodeContent(content: String): String? {
        val encrypted = SecureCrypto.encrypt(content.toByteArray(), this::onCryptoFailed);
        return encode(encrypted)
    }

    fun decodeContent(content: String): String {
        return SecureCrypto.decrypt(decode(content), this::onCryptoFailed).run {
            if (this == null) "" else String(this)
        }
    }

    private fun encode(bytes: ByteArray?): String? {
        return if (bytes == null) {
            null
        } else {
            Base64.encodeToString(bytes, Base64.NO_WRAP)
        }
    }

    private fun decode(content: String?): ByteArray? {
        return if (content == null) {
            null
        } else {
            Base64.decode(content, Base64.NO_WRAP)
        }
    }

    private fun onCryptoFailed(tr: Throwable) {
        tr.printStackTrace()
        tr.toString().logd()
    }
}