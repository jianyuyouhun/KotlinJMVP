package com.jianyuyouhun.kotlin.library.mvp

import com.jianyuyouhun.kotlin.library.app.KTApp

/**
 * model基类
 * Created by wangyu on 2017/7/25.
 */
abstract class BaseKTModel {

    abstract fun onModelCreate(app: KTApp)

    open fun onModelDestroy() {}

    open fun onAllModelCreate() {}

    fun <MinorModel : BaseKTModel> getModel(model: Class<MinorModel>): MinorModel {
        return KTApp.mInstance.getKTModel(model)
    }
}