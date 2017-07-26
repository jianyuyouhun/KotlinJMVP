package com.jianyuyouhun.kotlin.library.app.exception

/**
 * 获取presenter异常捕获
 * Created by wangyu on 2017/7/25.
 */

class InitPresenterException @JvmOverloads constructor(detailMessage: String = "presenter初始化失败！") : RuntimeException(detailMessage)