package com.tian.lib_common.lib_network

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.telephony.TelephonyManager
import java.io.IOException
import java.net.HttpURLConnection
import java.net.NetworkInterface
import java.net.SocketException
import java.net.URL

object NetworkUtil {

    private const val url = "http://www.baidu.com"
    private const val NET_CNNT_BAIDU_OK = 1
    private const val NET_CNNT_BAIDU_TIMEOUT = 2
    private const val NET_NOT_PREPARE = 3
    private const val NET_ERROR = 4
    private const val TIMEOUT = 3000

    /**
     * Checks if network is available.
     *
     * @param context The context to use for checking network status.
     * @return True if network is available, false otherwise.
     */
    @SuppressLint("ObsoleteSdkInt")
    fun isNetworkAvailable(context: Context): Boolean {
        val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
            ?: return false

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val capabilities = manager.getNetworkCapabilities(manager.activeNetwork)
            capabilities != null && (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                    || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                    || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET))
        } else {
            val info = manager.activeNetworkInfo
            info != null && info.isConnected
        }
    }

    fun getLocalIpAddress(): String {
        return try {
            NetworkInterface.getNetworkInterfaces().toList().flatMap { intf ->
                intf.inetAddresses.toList().filter { inetAddress ->
                    !inetAddress.isLoopbackAddress && inetAddress.hostAddress!!.indexOf(':') == -1
                }
            }.firstOrNull()?.hostAddress ?: "Unable to determine IP address"
        } catch (ex: SocketException) {
            ex.printStackTrace()
            "Unable to determine IP address"
        }
    }

    /**
     * Returns the current network state
     *
     * @param context Application context
     * @return Network status code
     */
    fun getNetState(context: Context): Int {
        return try {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)

            if (networkCapabilities != null && networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)) {
                if (!connectionNetwork()) {
                    NET_CNNT_BAIDU_TIMEOUT
                } else {
                    NET_CNNT_BAIDU_OK
                }
            } else {
                NET_NOT_PREPARE
            }
        } catch (e: Exception) {
            e.printStackTrace()
            NET_ERROR
        }
    }

    /**
     * Pings "http://www.baidu.com"
     *
     * @return True if connection is successful, otherwise false
     */
    private fun connectionNetwork(): Boolean {
        var result = false
        var httpUrl: HttpURLConnection? = null

        try {
            val url = URL(url)
            httpUrl = url.openConnection() as HttpURLConnection
            httpUrl.connectTimeout = TIMEOUT
            httpUrl.connect()
            result = true
        } catch (e: IOException) {
            // Handle IOException if needed
        } finally {
            httpUrl?.disconnect()
        }

        return result
    }

    /**
     * Check if the current network is 3G.
     *
     * @param context Application context.
     * @return true if the network is 3G, false otherwise.
     */
    fun is3G(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetInfo = connectivityManager.activeNetworkInfo
        return activeNetInfo != null && activeNetInfo.type == ConnectivityManager.TYPE_MOBILE &&
                (activeNetInfo.subtype == TelephonyManager.NETWORK_TYPE_UMTS ||
                        activeNetInfo.subtype == TelephonyManager.NETWORK_TYPE_HSDPA ||
                        activeNetInfo.subtype == TelephonyManager.NETWORK_TYPE_HSUPA ||
                        activeNetInfo.subtype == TelephonyManager.NETWORK_TYPE_HSPA)
    }

    /**
     * Check if the current network is 2G.
     *
     * @param context Application context.
     * @return true if the network is 2G, false otherwise.
     */
    fun is2G(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetInfo = connectivityManager.activeNetworkInfo
        return activeNetInfo != null && activeNetInfo.type == ConnectivityManager.TYPE_MOBILE &&
                (activeNetInfo.subtype == TelephonyManager.NETWORK_TYPE_GPRS ||
                        activeNetInfo.subtype == TelephonyManager.NETWORK_TYPE_EDGE ||
                        activeNetInfo.subtype == TelephonyManager.NETWORK_TYPE_CDMA ||
                        activeNetInfo.subtype == TelephonyManager.NETWORK_TYPE_1xRTT ||
                        activeNetInfo.subtype == TelephonyManager.NETWORK_TYPE_IDEN)
    }

    /**
     * Check if the current network is WiFi using NetworkCapabilities.
     *
     * @param context Application context.
     * @return true if the network is WiFi, false otherwise.
     */
    fun isWifiNetwork(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
    }

    /**
     * Check if the current network is Cellular using NetworkCapabilities.
     *
     * @param context Application context.
     * @return true if the network is Cellular, false otherwise.
     */
    fun isCellularNetwork(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
    }
}