package com.jianyuyouhun.kotlin.library.mvp

/**
 * BaseKTView接口
 * Created by wangyu on 2017/7/25.
 */
interface BaseKTView {
    fun showToast(msg: String)
    fun showProgressDialog()
    fun dismissProgressDialog()
}