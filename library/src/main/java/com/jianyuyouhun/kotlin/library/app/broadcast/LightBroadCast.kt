package com.jianyuyouhun.kotlin.library.app.broadcast

import android.os.Handler
import android.os.Looper
import android.os.Message

/**
 * 全局消息回调监听
 */
interface OnGlobalMsgReceiveListener {
    fun onReceiveGlobalMsg(msg: Message)
}

/**
 * 轻量级广播，使用Handler实现
 * Created by wangyu on 2017/7/26.
 */
class LightBroadCast private constructor() {

    companion object {
        private var hasInit = false
        private lateinit var lightBroadCast: LightBroadCast

        fun getInstance(): LightBroadCast {
            if (hasInit) {
                return lightBroadCast
            } else {
                throw RuntimeException("请在app中初始化 LightBroadCast")
            }
        }

        @Synchronized fun init() {
            if (hasInit) {
                return
            }
            lightBroadCast = LightBroadCast()
            hasInit = true
        }
    }

    val receiveListeners: ArrayList<OnGlobalMsgReceiveListener> = ArrayList()

    val uiHandler: Handler = object: Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            for (receiveListener in receiveListeners) {
                receiveListener.onReceiveGlobalMsg(msg!!)
            }
        }
    }

    fun addOnGlobalMsgReceiveListener(listener: OnGlobalMsgReceiveListener) {
        receiveListeners.add(listener)
    }

    fun removeOnGlobalMsgReceiveListener(listener: OnGlobalMsgReceiveListener) {
        receiveListeners.remove(listener)
    }

    fun sendEmptyMsg(msgWhat: Int) {
        uiHandler.sendEmptyMessage(msgWhat)
    }

    fun sendEmptyMsgDelayed(msgWhat: Int, delayMillis: Long) {
        uiHandler.sendEmptyMessageDelayed(msgWhat, delayMillis)
    }

    fun sendEmptyMsgAtTime(msgWhat: Int, upTimeMillis: Long) {
        uiHandler.sendEmptyMessageAtTime(msgWhat, upTimeMillis)
    }

    fun post(r: Runnable) {
        uiHandler.post(r)
    }

    fun postDelayed(r: Runnable, delayMillis: Long) {
        uiHandler.postDelayed(r, delayMillis)
    }

    fun postAtTime(r: Runnable, upTimeMillis: Long) {
        uiHandler.postAtTime(r, upTimeMillis)
    }

    fun postAtFrontOfQueue(r: Runnable) {
        uiHandler.postAtFrontOfQueue(r)
    }

    fun sendMessage(msg: Message) {
        uiHandler.sendMessage(msg)
    }

    fun sendMessageDelayed(msg: Message, delayMillis: Long) {
        uiHandler.sendMessageDelayed(msg, delayMillis)
    }

    fun sendMessageAtTime(msg: Message, upTimeMillis: Long) {
        uiHandler.sendMessageAtTime(msg, upTimeMillis)
    }

    fun sendMessageAtFrontOfQueue(msg: Message) {
        uiHandler.sendMessageAtFrontOfQueue(msg)
    }
}