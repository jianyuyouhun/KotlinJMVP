package com.jianyuyouhun.kotlin.library.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityManager
import android.app.Service
import android.content.Context
import android.telephony.TelephonyManager
import android.text.TextUtils
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import com.jianyuyouhun.kotlin.library.app.KTApp
import java.net.NetworkInterface
import java.net.SocketException
import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * 通用工具类
 * Created by wangyu on 2017/7/25.
 */
class CommonUtils {
    companion object {
        val TAG : String = "CommonUtils"
        var mScreenDPI = -1
        var mSimCardState = -1
        var mDeviceVersion : String? = ""
        var mIMEI : String = ""
        var mSDKVersion = 0
        var mFloatDeviceVersion : Float = 0f
        var mPhoneVersion: String = ""
        var mPhoneStyle: String = ""
        var mDisplayMetrics: DisplayMetrics? = null
        var mScreenWidth = -1
        var mScreenHeight = -1
        /**
         * 获取当前进程名字
         */
        fun getUIPName(context: Context): String {
            val pid = android.os.Process.myPid()
            val activityManager: ActivityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            activityManager.runningAppProcesses
                    .filter { it.pid == pid }
                    .forEach { return it.processName }
            return ""
        }

        /**
         * 获取版本号
         */
        fun getStringDeviceVersion(): String {
            mDeviceVersion = android.os.Build.VERSION.RELEASE
            return mDeviceVersion!!
        }

        /**
         * 获取数字版本号
         */
        fun getIntDeviceVersion(): Float {
            mDeviceVersion = android.os.Build.VERSION.RELEASE
            if (mDeviceVersion != null && mDeviceVersion!!.length >= 3) {
                val splitString: String = mDeviceVersion!!.substring(0, 3)
                val pattern: Pattern = Pattern.compile("^\\d+([\\.]?\\d+)?$")
                val matcher: Matcher = pattern.matcher(splitString)
                val result: Boolean = matcher.matches()
                if (result) {
                    mFloatDeviceVersion = java.lang.Float.valueOf(splitString)
                } else{
                    mFloatDeviceVersion = 0f
                }
            }
            return mFloatDeviceVersion
        }

        /**
         * 获取手机版本号
         */
        fun getPhoneVersion(): String {
            mPhoneVersion = android.os.Build.VERSION.CODENAME
            return mPhoneVersion
        }

        /**
         * 获取设备Android系统版本
         */
        fun getDeviceSDK(): Int {
            mSDKVersion = android.os.Build.VERSION.SDK_INT
            return mSDKVersion
        }

        /**
         * 获取IMEI值
         */
        @SuppressLint("HardwareIds")
        fun getIMEI(): String {
            val tm: TelephonyManager = KTApp.mInstance.getSystemService(Service.TELEPHONY_SERVICE) as TelephonyManager
            mIMEI = if (TextUtils.isEmpty(tm.deviceId)) "" else tm.deviceId
            return mIMEI
        }

        /**
         * 获取手机型号
         */
        fun getPhoneStyle(): String {
            mPhoneStyle = android.os.Build.MODEL
            return mPhoneStyle
        }

        /**
         * 获取手机ip地址
         */
        fun getLocalIpAddress(): String {
            try {
                val en = NetworkInterface.getNetworkInterfaces()
                while (en.hasMoreElements()) {
                    val intf = en.nextElement()
                    val enumIpAddr = intf.inetAddresses
                    while (enumIpAddr.hasMoreElements()) {
                        val inetAddress = enumIpAddr.nextElement()
                        if (!inetAddress.isLoopbackAddress) {
                            return inetAddress.hostAddress.toString()
                        }
                    }
                }
            } catch (ex: SocketException) {
                ex.printStackTrace()
            }
            return ""
        }

        /**
         * 展开键盘
         */
        fun showSoftInput(context: Context, focusView: View) {
            focusView.requestFocus()
            focusView.postDelayed(Runnable {
                val inputMgr = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMgr.showSoftInput(focusView, 0)
            }, 200)
        }

        /**
         * 隐藏键盘
         */
        fun hideSoftInput(activity: Activity) {
            if (activity.currentFocus != null) {
                val inputMgr = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMgr.hideSoftInputFromWindow(activity.currentFocus.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
            }
        }

        /**
         * dp转像素
         */
        fun dipToPx(context: Context, dipValue: Float): Int {
            val density = context.resources.displayMetrics.density
            return (dipValue * density + 0.5f).toInt()
        }

        private fun initDisplayMetrics() {
            if (mDisplayMetrics == null) {
                mDisplayMetrics = DisplayMetrics()
                val wndMgr = KTApp.mInstance.getSystemService(Context.WINDOW_SERVICE) as WindowManager
                wndMgr.defaultDisplay.getMetrics(mDisplayMetrics)
            }
        }

        /**
         * 获取屏幕宽度
         */
        fun getScreenWidth(): Int {
            if (mScreenWidth == -1) {
                initDisplayMetrics()
                mScreenWidth = mDisplayMetrics!!.widthPixels
            }
            return mScreenWidth
        }

        /**
         * 获取屏幕高度
         */
        fun getScreenHeight(): Int {
            if (mScreenHeight == -1) {
                initDisplayMetrics()
                mScreenHeight = mDisplayMetrics!!.heightPixels
            }
            return mScreenHeight
        }

        /**
         * app是否在后台运行
         */
        fun isRunAtBackground(context: Context): Boolean {
            val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            val appProcesses = activityManager.runningAppProcesses
            appProcesses
                    .filter { it.processName == context.packageName }
                    .forEach { return it.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_BACKGROUND }
            return false
        }
    }
}