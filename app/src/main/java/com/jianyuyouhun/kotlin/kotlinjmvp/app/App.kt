package com.jianyuyouhun.kotlin.kotlinjmvp.app

import com.jianyuyouhun.kotlin.kotlinjmvp.mvp.TestModel
import com.jianyuyouhun.kotlin.library.app.KTApp
import com.jianyuyouhun.kotlin.library.mvp.BaseKTModel

/**
 *
 * Created by wangyu on 2017/7/25.
 */
class App: KTApp() {

    override fun onCreate() {
        super.onCreate()
    }

    override fun initModels(models: ArrayList<BaseKTModel>) {
        models.add(TestModel())
    }

    override fun setDebugMode(): Boolean {
        return super.setDebugMode()
    }
}