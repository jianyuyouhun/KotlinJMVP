package com.jianyuyouhun.kotlin.library.utils.proxy

import com.jianyuyouhun.kotlin.library.app.BaseActivity
import com.jianyuyouhun.kotlin.library.app.KTApp
import com.jianyuyouhun.kotlin.library.mvp.BaseKTModel
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * model延时加载策略
 * Created by wangyu on 2017/11/1.
 */

fun <Model : BaseKTModel> bindModel(cls: Class<Model>)
    : ReadOnlyProperty<BaseActivity, Model> = require(cls.name, modelFinder)

private val modelFinder: BaseActivity.(String) -> BaseKTModel?
    get() = { KTApp.mInstance.getKTModel(it) }

private fun modelNotFound(name: String, desc: KProperty<*>): Nothing =
        throw IllegalStateException("Model Name $name for '${desc.name}' not found.")

@Suppress("UNCHECKED_CAST")
private fun <T, Model : BaseKTModel> require(name: String, finder: T.(String) -> BaseKTModel?)
    = LazyROP { t: T, desc -> t.finder(name) as Model? ?: modelNotFound(name, desc) }