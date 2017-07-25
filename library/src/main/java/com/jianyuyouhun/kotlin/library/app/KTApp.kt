package com.jianyuyouhun.kotlin.library.app

import android.app.Application
import com.jianyuyouhun.kotlin.library.app.exception.ExceptionCaughtAdapter
import com.jianyuyouhun.kotlin.library.mvp.BaseKTModel
import com.jianyuyouhun.kotlin.library.utils.CommonUtils
import com.jianyuyouhun.kotlin.library.utils.Logger

/**
 * application基类
 * Created by wangyu on 2017/7/25.
 */
abstract class KTApp : Application() {

    companion object {
        val TAG: String = "KTApp"

        var mInstance: KTApp? = null
        var isDebug: Boolean = false

    }

    var mIsMainProcess = false

    var modelsMap : MutableMap<String, BaseKTModel> = HashMap()

    override fun onCreate() {
        super.onCreate()
        mInstance = if (mInstance == null) this else return
        BuildConfig.IS_DEBUG = setDebugMode()
        isDebug = BuildConfig.IS_DEBUG
        initExceptionCatch()
        initDependencies()
        val pidName = CommonUtils.getUIPName(this)
        mIsMainProcess = pidName == packageName
        initKTApp()
    }


    /**
     * 初始化第三方依赖
     */
    fun initDependencies() {

    }

    /**
     * 设置调试模式参数
     */
    fun setDebugMode(): Boolean {
        return true
    }

    /**
     * 初始化异常捕获逻辑
     */
    fun initExceptionCatch() {
        if (isDebug) {
            val handler: Thread.UncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler()
            val exceptionCaughtAdapter = ExceptionCaughtAdapter(handler)
            Thread.setDefaultUncaughtExceptionHandler(exceptionCaughtAdapter)
        }
    }

    private fun initKTApp() {
        val models: List<BaseKTModel> = ArrayList()
        initModels(models)
        for (model in models) {
            val time = System.currentTimeMillis()
            model.onModelCreate(this)
            val modelClass = model.javaClass
            val modelName = modelClass.name
            modelsMap.put(modelName, model)
            val spendTime = System.currentTimeMillis() - time
            Logger.e(TAG, modelClass.simpleName + "启动耗时(毫秒)：" + spendTime)
        }
        for (model in models) {
            model.onAllModelCreate()
        }
    }

    /**
     * 初始化model
     */
    abstract fun initModels(models: List<BaseKTModel>)

    @Suppress("UNCHECKED_CAST")
    fun <Model : BaseKTModel> getKTModel(model: Class<Model>): Model {
        return modelsMap[model.name] as Model
    }

}