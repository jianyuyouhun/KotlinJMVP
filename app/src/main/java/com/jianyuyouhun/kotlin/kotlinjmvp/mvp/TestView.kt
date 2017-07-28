package com.jianyuyouhun.kotlin.kotlinjmvp.mvp

import com.jianyuyouhun.kotlin.library.mvp.BaseKTView

/**
 *
 * Created by wangyu on 2017/7/26.
 */
interface TestView: BaseKTView {
    fun onSuccess(data: String)
}