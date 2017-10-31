package com.jianyuyouhun.kotlin.library.mvp

import android.app.Application
import com.jianyuyouhun.kotlin.library.app.KTApp

/**
 * model基类
 * Created by wangyu on 2017/7/25.
 */
abstract class BaseKTModel {

    abstract fun onModelCreate(app: Application)

    open fun onModelDestroy() {}

    open fun onAllModelCreate() {}

    fun <MinorModel : BaseKTModel> getModel(model: Class<MinorModel>): MinorModel = KTApp.mInstance.getKTModel(model)
}