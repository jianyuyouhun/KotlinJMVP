package com.jianyuyouhun.kotlin.library.mvp

import android.app.Application

/**
 * model基类
 * Created by wangyu on 2017/7/25.
 */
abstract class BaseKTModel {

    abstract fun onModelCreate(app: Application)

    open fun onModelDestroy() {}

    open fun onAllModelCreate() {}

}