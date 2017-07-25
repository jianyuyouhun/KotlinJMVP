package com.jianyuyouhun.kotlin.library.mvp

import android.os.Handler


/**
 * 通用接口回调
 * Created by wangyu on 2017/7/25.
 */
interface OnResultListener<T> {
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
    abstract class OnDelayResultListener<T>(delay: Long) : OnResultListener<T> {
        val mHandler = Handler()
        var mDelay = delay

        override fun onResult(result: Int, info: T) {
            mHandler.postDelayed(Runnable{
                onDelayResult(result, info)
            }, mDelay)
        }

        abstract fun onDelayResult(result: Int, info: T)
    }

}