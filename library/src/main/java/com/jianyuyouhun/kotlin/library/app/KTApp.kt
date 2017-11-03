package com.jianyuyouhun.kotlin.library.app

import android.app.Application
import com.jianyuyouhun.kotlin.library.BuildConfig
import com.jianyuyouhun.kotlin.library.app.broadcast.LightBroadCast
import com.jianyuyouhun.kotlin.library.app.exception.ExceptionCaughtAdapter
import com.jianyuyouhun.kotlin.library.mvp.BaseKTModel
import com.jianyuyouhun.kotlin.library.mvp.common.CacheModel
import com.jianyuyouhun.kotlin.library.mvp.common.ThemeModel
import com.jianyuyouhun.kotlin.library.utils.CommonUtils
import com.jianyuyouhun.kotlin.library.utils.Logger

/**
 * application基类
 * Created by wangyu on 2017/7/25.
 */
abstract class KTApp : Application() {

    companion object {

        val TAG: String = "KTApp"
        var isDebug: Boolean = false

        lateinit var mInstance: KTApp private set
    }

    var mIsMainProcess = false
        private set

    private var modelsMap : MutableMap<String, BaseKTModel> = HashMap()

    override fun onCreate() {
        super.onCreate()
        mInstance = this
        isDebug = setDebugMode()
        initExceptionCatch()
        initDependencies()
        val pidName = CommonUtils.getUIPName(this)
        mIsMainProcess = pidName == packageName
        initKTApp()
    }

    /**
     * 初始化第三方依赖
     */
    open fun initDependencies() {
        LightBroadCast.init()
    }

    /**
     * 设置调试模式参数
     */
    open fun setDebugMode(): Boolean = BuildConfig.DEBUG

    /**
     * 初始化异常捕获逻辑
     */
    open fun initExceptionCatch() {
        if (isDebug) {
            val handler: Thread.UncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler()
            val exceptionCaughtAdapter = ExceptionCaughtAdapter(handler)
            Thread.setDefaultUncaughtExceptionHandler(exceptionCaughtAdapter)
        }
    }

    private fun initKTApp() {
        val models: ArrayList<BaseKTModel> = ArrayList()
        initCommonModels(models)
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

    open fun initCommonModels(models: ArrayList<BaseKTModel>) {
        models.add(CacheModel())
        models.add(ThemeModel())
    }

    /**
     * 初始化model
     */
    abstract fun initModels(models: ArrayList<BaseKTModel>)

    @Suppress("UNCHECKED_CAST")
    fun <Model : BaseKTModel> getKTModel(model: Class<Model>): Model = getKTModel(model.name)

    @Suppress("UNCHECKED_CAST")
    fun <Model : BaseKTModel> getKTModel(modelName : String): Model = modelsMap[modelName] as Model
}