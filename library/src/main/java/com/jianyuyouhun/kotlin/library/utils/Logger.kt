package com.jianyuyouhun.kotlin.library.utils

import android.util.Log
import com.jianyuyouhun.kotlin.library.app.KTApp

/**
 * 日志打印
 * Created by wangyu on 2017/7/25.
 */
class Logger {
   companion object {
       fun e(tag: String, msg: String) {
           if (KTApp.isDebug) {
               Log.e(tag, msg)
           }
       }
       fun i(tag: String, msg: String) {
           if (KTApp.isDebug) {
               Log.i(tag, msg)
           }
       }
       fun d(tag: String, msg: String) {
           if (KTApp.isDebug) {
               Log.d(tag, msg)
           }
       }
       fun w(tag: String, msg: String) {
           if (KTApp.isDebug) {
               Log.w(tag, msg)
           }
       }
   }
}