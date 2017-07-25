package com.jianyuyouhun.kotlin.library.mvp

import com.jianyuyouhun.kotlin.library.app.KTApp

/**
 * model基类
 * Created by wangyu on 2017/7/25.
 */
open class BaseKTModel {

    fun onModelCreate(app: KTApp) {}

    fun onModelDestroy() {}

    fun onAllModelCreate() {}

    fun <MinorModel : BaseKTModel> getModel(model: Class<MinorModel>): MinorModel {
        return KTApp.mInstance!!.getKTModel(model)
    }
}