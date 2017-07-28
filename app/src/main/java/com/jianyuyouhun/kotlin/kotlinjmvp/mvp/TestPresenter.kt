package com.jianyuyouhun.kotlin.kotlinjmvp.mvp

import android.content.Context
import com.jianyuyouhun.kotlin.library.mvp.BaseKTPresenter
import com.jianyuyouhun.kotlin.library.mvp.OnResultListener

/**
 * 测试Presenter
 * Created by wangyu on 2017/7/26.
 */
class TestPresenter: BaseKTPresenter<TestModel, TestView>() {

    override fun onCreate(context: Context) {
        super.onCreate(context)
    }

    fun start() {
        if (isAttach()) {
            getKTView()!!.showProgressDialog()
            mModel!!.getDataByLambda{ result, data ->
                getKTView()!!.dismissProgressDialog()
                if (result == OnResultListener.RESULT_SUCCESS) {
                    getKTView()!!.onSuccess(data)
                } else {
                    getKTView()!!.showToast("net error")
                }
            }
        }
    }
}