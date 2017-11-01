package com.jianyuyouhun.kotlin.library.utils.proxy

import android.view.View
import com.jianyuyouhun.kotlin.library.utils.SimpleBaseAdapter.ViewHolder
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 *  kotterKnife扩展类，为SimpleBaseAdapter增加延时加载策略
 * Created by wangyu on 2017/10/31.
 */

fun <V : View> ViewHolder.bindView(id: Int)
        : ReadOnlyProperty<ViewHolder, V> = required(id, viewFinder)

fun <V : View> ViewHolder.bindOptionalView(id: Int)
        : ReadOnlyProperty<ViewHolder, V?> = optional(id, viewFinder)

fun <V : View> ViewHolder.bindViews(vararg ids: Int)
        : ReadOnlyProperty<ViewHolder, List<V>> = required(ids, viewFinder)

fun <V : View> ViewHolder.bindOptionalViews(vararg ids: Int)
        : ReadOnlyProperty<ViewHolder, List<V>> = optional(ids, viewFinder)

private val ViewHolder.viewFinder: ViewHolder.(Int) -> View?
    get() = { itemView.findViewById(it) }

private fun viewNotFound(id:Int, desc: KProperty<*>): Nothing =
        throw IllegalStateException("View ID $id for '${desc.name}' not found.")

@Suppress("UNCHECKED_CAST")
private fun <T, V : View> required(id: Int, finder: T.(Int) -> View?)
        = LazyROP { t: T, desc -> t.finder(id) as V? ?: viewNotFound(id, desc) }

@Suppress("UNCHECKED_CAST")
private fun <T, V : View> optional(id: Int, finder: T.(Int) -> View?)
        = LazyROP { t: T, desc -> t.finder(id) as V? }

@Suppress("UNCHECKED_CAST")
private fun <T, V : View> required(ids: IntArray, finder: T.(Int) -> View?)
        = LazyROP { t: T, desc -> ids.map { t.finder(it) as V? ?: viewNotFound(it, desc) } }

@Suppress("UNCHECKED_CAST")
private fun <T, V : View> optional(ids: IntArray, finder: T.(Int) -> View?)
        = LazyROP { t: T, desc -> ids.map { t.finder(it) as V? }.filterNotNull() }