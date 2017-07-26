package com.jianyuyouhun.kotlin.library.app.exception

/**
 * 异常捕获适配器
 * Created by wangyu on 2017/7/25.
 */
class ExceptionCaughtAdapter(handler: Thread.UncaughtExceptionHandler) : Thread.UncaughtExceptionHandler{

    var mHandler: Thread.UncaughtExceptionHandler = handler

    override fun uncaughtException(t: Thread?, e: Throwable?) {
        ExceptionActivity.showException(e!!)
        mHandler.uncaughtException(t, e)
    }
}