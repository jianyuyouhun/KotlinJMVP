package com.jianyuyouhun.kotlin.library.app

import android.os.Bundle
import com.jianyuyouhun.kotlin.library.app.exception.InitPresenterException
import com.jianyuyouhun.kotlin.library.mvp.BaseKTModel
import com.jianyuyouhun.kotlin.library.mvp.BaseKTPresenter
import java.lang.reflect.ParameterizedType

/**
 * MVPActivity基类
 * Created by wangyu on 2017/7/25.
 */
abstract class BaseMVPActivity<MajorPresenter: BaseKTPresenter<*, *>, MajorModel: BaseKTModel>: BaseActivity() {

    lateinit var mPresenter: MajorPresenter
    var mModel: MajorModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        @Suppress("UNCHECKED_CAST")
        val majorPresenterCls = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<MajorPresenter>
        try {
            mPresenter = majorPresenterCls.newInstance()
        } catch (ex: InstantiationException) {
            ex.printStackTrace()
            throw InitPresenterException()
        } catch (ex: IllegalAccessException) {
            ex.printStackTrace()
            throw InitPresenterException("请确保" + majorPresenterCls.simpleName + "拥有public的无参构造函数");
        }
        mModel = initModel()
        bindModelAndView(mPresenter)
        if (!mPresenter.isAttach()) {
            throw InitPresenterException("请为" + mPresenter.javaClass.name + "绑定数据")
        }
        mPresenter.onCreate(this)
    }

    abstract fun bindModelAndView(presenter: MajorPresenter)

    abstract fun initModel(): MajorModel?

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.onDestroy()
    }
}