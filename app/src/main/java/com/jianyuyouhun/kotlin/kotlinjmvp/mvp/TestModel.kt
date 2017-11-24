package com.jianyuyouhun.kotlin.kotlinjmvp.mvp

import android.app.Application
import android.os.Message
import com.jianyuyouhun.kotlin.library.app.broadcast.LightBroadCast
import com.jianyuyouhun.kotlin.library.mvp.BaseKTModel
import com.jianyuyouhun.kotlin.library.mvp.OnResultListener
import com.jianyuyouhun.kotlin.library.utils.lgD

/**
 *
 * Created by wangyu on 2017/7/26.
 */
class TestModel: BaseKTModel() {

    private val handler by lazy { LightBroadCast.instance }

    private val onGlobalMsgReceiveListener = {msg: Message? ->
        lgD("globalMsg", ""+msg?.what)
    }

    override fun onModelCreate(app: Application) {
        handler.addOnGlobalMsgReceiveListener(onGlobalMsgReceiveListener)
    }

    fun getDataByLambda(onResult: (result: Int, data: String) -> Unit) {
        handler.postDelayed(Runnable {
            onResult(OnResultListener.RESULT_SUCCESS, "get data success")
            handler.sendEmptyMsg(1)
        }, 2000)
    }
}