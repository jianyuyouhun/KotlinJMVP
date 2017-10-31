package com.jianyuyouhun.kotlin.kotlinjmvp.mvp

import android.app.Application
import android.os.Message
import com.jianyuyouhun.kotlin.library.app.broadcast.LightBroadCast
import com.jianyuyouhun.kotlin.library.app.broadcast.OnGlobalMsgReceiveListener
import com.jianyuyouhun.kotlin.library.mvp.BaseKTModel
import com.jianyuyouhun.kotlin.library.mvp.OnResultListener
import com.jianyuyouhun.kotlin.library.utils.Logger

/**
 *
 * Created by wangyu on 2017/7/26.
 */
class TestModel: BaseKTModel() {

    lateinit var handler: LightBroadCast

    val onGlobalMsgReceiveListener: OnGlobalMsgReceiveListener = object : OnGlobalMsgReceiveListener {
        override fun onReceiveGlobalMsg(msg: Message) {
            Logger.d("globalMsg", ""+msg.what)
        }
    }

    override fun onModelCreate(app: Application) {
        handler = LightBroadCast.getInstance()
        handler.addOnGlobalMsgReceiveListener(onGlobalMsgReceiveListener)
    }

    fun getDataByLambda(onResult: (result: Int, data: String) -> Unit) {
        handler.postDelayed(Runnable {
            onResult(OnResultListener.RESULT_SUCCESS, "get data success")
            handler.sendEmptyMsg(1)
        }, 2000)
    }
}