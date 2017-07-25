package com.jianyuyouhun.kotlin.library.app

import android.app.Application
import com.jianyuyouhun.kotlin.library.app.exception.ExceptionCaughtAdapter

/**
 * application基类
 * Created by wangyu on 2017/7/25.
 */
abstract class KTApp : Application() {

    companion object {
        val TAG: String = "KTApp"

        var mInstance: KTApp? = null
        var isDebug: Boolean = false
    }

    var modelsMap : Map<String, Object> = HashMap()

    override fun onCreate() {
        super.onCreate()
        mInstance = if (mInstance == null) this else return
        BuildConfig.IS_DEBUG = setDebugMode()
        isDebug = BuildConfig.IS_DEBUG
        initExceptionCatch()
        initDependencies()

    }

    fun initDependencies() {

    }

    fun setDebugMode(): Boolean {
        return true
    }

    fun initExceptionCatch() {
        if (isDebug) {
            val handler: Thread.UncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler()
            val exceptionCaughtAdapter = ExceptionCaughtAdapter(handler)
            Thread.setDefaultUncaughtExceptionHandler(exceptionCaughtAdapter)
        }
    }

}