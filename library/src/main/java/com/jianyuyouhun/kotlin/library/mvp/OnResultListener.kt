package com.jianyuyouhun.kotlin.library.mvp

import android.os.Handler

/**
 * 通用接口回调
 * Created by wangyu on 2017/7/25.
 */
interface OnResultListener<in T> {
    companion object {
        /** 请求成功 */
        val RESULT_SUCCESS = 0
        /** 请求失败 */
        val RESULT_FAILED = -1
        /** 无数据 */
        val RESULT_NO_DATA = -2
    }

    /**
     * 结果回调方法
     */
    fun onResult(result: Int, info: T)

    /**
     * 延时回调接口
     */
    abstract class OnDelayResultListener<in T>(val delay: Long) : OnResultListener<T> {
        private val mHandler = Handler()

        final override fun onResult(result: Int, info: T) {
            mHandler.postDelayed({
                onDelayResult(result, info)
            }, delay)
        }

        abstract fun onDelayResult(result: Int, info: T)
    }

}